package com.stock.service.analytic.stock.analytic_helper;

import com.stock.model.stock.analytic.RiskType;

public class InterpreterHelper {
    public static RiskType riskInterpreter(double risk) {
        risk = Math.abs(risk);
        double diff = Math.abs(risk - 1);
        if(diff <= 0.2) {
            return RiskType.Balanced;
        } else if (diff > 0.2 && risk < 0.8) {
            return RiskType.Conservative;
        } else {
            return RiskType.Aggressive;
        }
    }
}
