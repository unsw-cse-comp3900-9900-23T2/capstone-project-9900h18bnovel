package com.example.novel_backend.core.interceptor;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.example.novel_backend.core.common.constant.ErrorCodeEnum;
import com.example.novel_backend.core.common.resp.RestResp;
import com.example.novel_backend.core.util.IpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Traffic Limiting Interceptor: Enables interface anti-scrubbing and flow limiting
 *
 * @author :Contanstin
 * @create 2023/8/2 13:57
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FlowLimitInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    /**
     * novel all resource
     */
    private static final String NOVEL_RESOURCE = "novelResource";

    static {
        // Interface flow-limiting rules: all requests,
        // limiting the maximum number of 4000 per second, exceeding the limit of even speed queuing
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(NOVEL_RESOURCE);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 2000.
        rule1.setCount(4000);
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);

        // Interface anti-scrubbing rule 1: all requests,
        // limit each IP can only pass a maximum of 50 per second, beyond the limit of direct rejection
        ParamFlowRule rule2 = new ParamFlowRule(NOVEL_RESOURCE)
                .setParamIdx(0)
                .setCount(50);
        // Interface anti-scrubbing rule 2: all requests,
        // limit each IP can only pass a maximum of 2,000 per minute, beyond the limit direct rejection
        ParamFlowRule rule3 = new ParamFlowRule(NOVEL_RESOURCE)
                .setParamIdx(0)
                .setCount(2000)
                .setDurationInSec(60);
        ParamFlowRuleManager.loadRules(Arrays.asList(rule2, rule3));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String ip = IpUtils.getRealIp(request);
        Entry entry = null;
        try {
            // If you need to configure exception entries, the parameters passed in support only basic types.
            // EntryType represents the type of traffic, where the system rules only take effect for IN types.
            // count is 1 in most cases, which means that the count is for a single call.
            entry = SphU.entry(NOVEL_RESOURCE, EntryType.IN, 1, ip);
            // Your logic here.
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (BlockException ex) {
            // Handle request rejection.
            log.info("IP:{}is limited！", ip);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter()
                    .write(objectMapper.writeValueAsString(RestResp.fail(ErrorCodeEnum.USER_REQ_MANY)));
        } finally {
            // When exit, you must also bring the corresponding parameter, otherwise there may be statistical errors.
            if (entry != null) {
                entry.exit(1, ip);
            }
        }
        return false;
    }

}
