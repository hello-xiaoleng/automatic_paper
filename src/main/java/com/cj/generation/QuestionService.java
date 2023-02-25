/*
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.cj.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuanpu
 * @version QuestionService.java, v 0.1 2023年01月31日 9:23 PM xuanpu
 */
public class QuestionService {
    public static QuestionBean[] getQuestionArray(int type, String substring) {
        String[] split = substring.split(",");

        List<QuestionBean> questionBeans = new ArrayList<>();
        for (String point : split) {
            List<QuestionBean> list = DB.pointQuestionIndex.get(type + "_" + point.trim());
            if (list != null) {
                questionBeans.addAll(list);
            }
        }
        return questionBeans.toArray(new QuestionBean[] {});
    }

    public static List<QuestionBean> getQuestionListWithOutSId(QuestionBean tmpQuestion) {
        return DB.questions.stream().filter(e -> e.getType() == tmpQuestion.getType()).collect(Collectors.toList());
    }
}