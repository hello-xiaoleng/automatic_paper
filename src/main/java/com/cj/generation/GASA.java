/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuanpu
 * @version GASA.java, v 0.1 2023年02月25日 10:05 PM xuanpu
 */
public class GASA {

    /**
     * 变异概率
     */
    private static final double  mutationRate   = 0.0085;
    /**
     * 精英主义
     */
    private static final boolean elitism        = false;
    /**
     * 淘汰数组大小
     */
    private static final int     tournamentSize = 5;

    // 进化种群
    public static Population evolvePopulation(Population pop, RuleBean rule) {
        Population newPopulation = new Population(pop.getLength());
        int elitismOffset = 0;
        // 种群交叉操作，从当前的种群pop来创建下一代种群newPopulation
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            // 较优选择parent
            Paper parent1 = select(pop);
            Paper parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                parent2 = select(pop);
            }
            // 交叉
            Paper child = crossover(parent1, parent2, rule);
            child.setId(i);
            newPopulation.setPaper(i, child);
        }
        // 种群变异操作
        Paper tmpPaper;
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            tmpPaper = newPopulation.getPaper(i);
            mutate(tmpPaper);
            // 计算知识点覆盖率与适应度
            tmpPaper.setKpCoverage(rule);
            tmpPaper.setAdaptationDegree(rule, Global.KP_WEIGHT, Global.DIFFCULTY_WEIGHt);
        }
        return newPopulation;
    }

    private static int getTypeByIndex(int index, RuleBean rule) {
        int type = 0;
        // 单选
        if (index < rule.getSingleNum()) {
            type = 1;
        } else if (index < rule.getSingleNum() + rule.getMultipleNum()) {
            // 填空
            type = 2;
        } else if (index < rule.getSingleNum() + rule.getMultipleNum() + rule.getCompleteNum()) {
            // 填空
            type = 3;
        } else {
            // 问答题
            type = 4;
        }
        return type;
    }

    /**
     * 突变算子 每个个体的每个基因都有可能突变
     *
     * @param paper
     */
    public static void mutate(Paper paper) {
        QuestionBean tmpQuestion;
        List<QuestionBean> list;
        int index;
        for (int i = 0; i < paper.getQuestionSize(); i++) {
            if (Math.random() < mutationRate) {
                // 进行突变，第i道
                tmpQuestion = paper.getQuestion(i);
                // 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
                list = QuestionService.getQuestionListWithOutSId(tmpQuestion);
                if (list.size() > 0) {
                    // 随机获取一道
                    index = (int) (Math.random() * list.size());
                    // 设置分数
                    list.get(index).setScore(tmpQuestion.getScore());
                    paper.saveQuestion(i, list.get(index));
                }
            }
        }
    }

    /**
     * 轮盘赌
     * 选择算子
     *
     * @param population
     */
    private static Paper select(Population population) {
        //Paper[] papers = population.getPapers();
        //
        //double total = 0.0;
        //for (Paper elem : papers) {
        //    if (elem.getAdaptationDegree() == 0.0) {
        //        throw new RuntimeException("adaptationDegree is zero.");
        //    }
        //    total += elem.getAdaptationDegree();
        //}
        //double slice = total * Math.random();
        //double sum = 0;
        //for (Paper paper : papers) {
        //    sum += paper.getAdaptationDegree();
        //    if (sum > slice) {
        //        return paper;
        //    }
        //}
        //return population.getFitness();

        Population pop = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            pop.setPaper(i, population.getPaper((int) (Math.random() * population.getLength())));
        }
        return pop.getFitness();
    }

    /**
     * 交叉算子
     *
     * @param parent1
     * @param parent2
     * @return
     */
    public static Paper crossover(Paper parent1, Paper parent2, RuleBean rule) {
        Paper child = new Paper(parent1.getQuestionSize());
        int s1 = (int) (Math.random() * parent1.getQuestionSize());
        int s2 = (int) (Math.random() * parent1.getQuestionSize());

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = Math.min(s1, s2);
        int endPos = Math.max(s1, s2);

        // 继承parent2中未被child继承的question
        // 防止出现重复的元素
        String idString = rule.getPointIds().toString();
        for (int i = 0; i < startPos; i++) {
            final int index = i;
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.saveQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
                // getQuestionArray()用来选择指定类型的试题数组
                //QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString
                //        .indexOf("]")));
                String s = parent2.getQuestion(i).getPointIds().toString();
                QuestionBean[] singleArray = QuestionService.getQuestionArray(type, s
                        .substring(1, s.indexOf("]")));
                singleArray = Arrays.stream(singleArray).filter(e -> e.getId() != parent2.getQuestion(index).getId())
                        .toArray(QuestionBean[]::new);
                child.saveQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
            }
        }

        for (int i = startPos; i < endPos; i++) {
            child.saveQuestion(i, parent1.getQuestion(i));
        }

        for (int i = endPos; i < parent2.getQuestionSize(); i++) {
            final int index = i;

            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.saveQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
                //QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString
                //        .indexOf("]")));
                String s = parent2.getQuestion(i).getPointIds().toString();

                QuestionBean[] singleArray = QuestionService.getQuestionArray(type, s.substring(1, s
                        .indexOf("]")));

                singleArray = Arrays.stream(singleArray).filter(e -> e.getId() != parent2.getQuestion(index).getId())
                        .toArray(QuestionBean[]::new);
                child.saveQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
            }
        }

        return child;
    }

}