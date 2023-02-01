/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuanpu
 * @version DB.java, v 0.1 2023年01月31日 9:26 PM xuanpu
 */
public class DB {
    /// <summary>
    /// 题库
    /// </summary>
    public static List<QuestionBean> questions = new ArrayList<>();

    public static Map<String, List<QuestionBean>> pointQuestionIndex = new HashMap<>();

    static {
        init();
    }

    public static void init() {

        for (int i = 1; i <= 5000; i++) {
            QuestionBean model = new QuestionBean();

            model.setId(i);

            //试题难度系数取0.2到1之间的随机值
            double difficulty = ((int) (Math.random() * (100 - 20 + 1)) + 20) * 0.01;
            model.setDifficulty(difficulty);

            //单选题1分
            if (i < 1001) {
                model.setType(1);
            }

            //单选题2分
            if (i > 1000 && i < 2001) {
                model.setType(2);
            }

            //判断题2分
            if (i > 2000 && i < 3001) {
                model.setType(3);
            }

            //填空题1—4分
            if (i > 3000 && i < 4001) {
                model.setType(4);
            }

            //问答题分数为难度系数*10
            if (i > 4000) {
                model.setType(5);
            }

            //每题1到4个知识点
            int count = (int) (Math.random() * (4 - 1 + 1)) + 1;
            List<Long> points = new ArrayList<>();
            for (int j = 0; j < count; j++) {

                long point = (long) (Math.random() * (100 - 1 + 1)) + 1;
                points.add(point);

                List<QuestionBean> list = pointQuestionIndex.get(model.getType() + "_" + point);
                if (list == null) {
                    list = new ArrayList<>();
                    list.add(model);
                    pointQuestionIndex.put(model.getType() + "_" + point, list);
                } else {
                    list.add(model);
                }

            }
            model.setPointIds(points);

            questions.add(model);

        }
    }

}

