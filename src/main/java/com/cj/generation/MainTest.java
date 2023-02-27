/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 测试类
 *
 * @author lixiaolin
 * @createDate 2016-06-22 20:23
 */
public class MainTest {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {

            long start = System.currentTimeMillis();

            Paper paper = test();

            //System.out.println("耗时：" + String.valueOf(System.currentTimeMillis() - start));

            if (paper.getAdaptationDegree() >= 0.95) {
                //System.out.println("success");
            } else {
                //System.out.println("fail");
            }
        }
    }

    public static Paper test() {

        List<Double> fitness = new ArrayList<>();

        List<Double> difficulty = new ArrayList<>();

        List<Double> kp = new ArrayList<>();

        Paper resultPaper = null;
        // 迭代计数器
        int count = 0;
        int runCount = 50;
        // 适应度期望值
        double expand = 0.96;
        // 可自己初始化组卷规则rule
        RuleBean rule = getRule();

        // 打印组卷规则
        System.out.println("ruleBean:" + rule);

        // 初始化种群
        Population population = new Population(150, true, rule);
        System.out.println(
                "初始终适应度  " + population.getFitness().getAdaptationDegree() + "," + "知识点覆盖率：" + population.getFitness().getkPCoverage()
                        + ",难度系数：" + population.getFitness().getDifficulty());
        fitness.add(population.getFitness().getAdaptationDegree());
        difficulty.add(population.getFitness().getDifficulty());
        kp.add(population.getFitness().getkPCoverage());

        while (count < runCount && population.getFitness().getAdaptationDegree() < expand) {
            count++;
            population = GA.evolvePopulation(population, rule);
            System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree() + "," + "," + "知识点覆盖率："
                    + population.getFitness().getkPCoverage()
                    + ",难度系数：" + population.getFitness().getDifficulty());

            fitness.add(population.getFitness().getAdaptationDegree());
            difficulty.add(population.getFitness().getDifficulty());
            kp.add(population.getFitness().getkPCoverage());
        }
        System.out.println("进化结束，进化次数： " + count);
        System.out.println(population.getFitness().getAdaptationDegree());
        resultPaper = population.getFitness();

        //printPaper(resultPaper);

        System.out.println(fitness);
        System.out.println(difficulty);
        System.out.println(kp);

        return resultPaper;

    }

    private static RuleBean getRule() {
        RuleBean ruleBean = new RuleBean();

        ruleBean.setTotalMark(100);
        ruleBean.setDifficulty(0.8);

        ruleBean.setSingleNum(10);
        ruleBean.setSingleScore(2);

        ruleBean.setMultipleNum(10);
        ruleBean.setMultipleScore(3);

        ruleBean.setCompleteNum(5);
        ruleBean.setCompleteScore(5);

        ruleBean.setSubjectiveNum(5);
        ruleBean.setSubjectiveScore(5);

        Set<Long> points = new HashSet<>();

        while (points.size() < 30) {

            long point = (long) (Math.random() * (100 - 1 + 1)) + 1;

            points.add(point);

        }
        List<Long> sorted = points.stream().sorted(Long::compareTo).collect(Collectors.toList());

        String pointsStr = sorted.stream().map(Object::toString).collect(Collectors.joining(","));
        ruleBean.setPointIds(pointsStr);

        return ruleBean;

    }

    private static void printPaper(Paper paper) {
        System.out.println("知识点覆盖率：" + paper.getkPCoverage() + ",难度系数：" + paper.getDifficulty());

        System.out.println(paper.getQuestionList().stream().flatMap(e -> e.getPointIds().stream()).collect(Collectors.toSet()));
        for (QuestionBean question : paper.getQuestionList()) {

            System.out.println(question.toString());

        }
    }

}

