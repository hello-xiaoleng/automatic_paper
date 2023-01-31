/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

/**
 * 测试类
 *
 * @author lixiaolin
 * @createDate 2016-06-22 20:23
 */
public class MainTest {
    public static void main(String[] args) {
        Paper resultPaper = null;
        // 迭代计数器
        int count = 0;
        int runCount = 100;
        // 适应度期望值
        double expand = 0.98;
        // 可自己初始化组卷规则rule
        RuleBean rule = getRule();

        // 初始化种群
        Population population = new Population(20, true, rule);
        System.out.println("初次适应度  " + population.getFitness().getAdaptationDegree());
        while (count < runCount && population.getFitness().getAdaptationDegree() < expand) {
            count++;
            population = GA.evolvePopulation(population, rule);
            System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree());
        }
        System.out.println("进化次数： " + count);
        System.out.println(population.getFitness().getAdaptationDegree());
        resultPaper = population.getFitness();

        System.out.println(resultPaper);
    }

    private static RuleBean getRule() {
        RuleBean ruleBean = new RuleBean();

        ruleBean.setPointIds("1,2,3,4,5,6,7,8,9,10");

        ruleBean.setTotalMark(100);
        ruleBean.setDifficulty(0.8);

        ruleBean.setSingleNum(10);
        ruleBean.setSingleScore(2);

        ruleBean.setCompleteNum(10);
        ruleBean.setCompleteScore(3);

        ruleBean.setSubjectiveNum(10);
        ruleBean.setSubjectiveScore(5);

        return ruleBean;
    }

}

