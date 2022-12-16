package ru.job4j.map;

import java.util.*;

public class AnalyzeByMap {
    public static double averageScore(List<Pupil> pupils) {
        double sum = 0;
        int n = 0;
        for (Pupil p : pupils) {
            for (Subject s : p.subjects()) {
                sum += s.score();
                n++;
            }
        }
        return sum / n;
    }

    public static List<Label> averageScoreByPupil(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        for (Pupil p : pupils) {
            double sum = 0;
            int len = p.subjects().size();
            for (int i = 0; i < len; i++) {
                sum += p.subjects().get(i).score();
            }
            rsl.add(new Label(p.name(), sum / len));
        }
        return rsl;
    }

    public static List<Label> averageScoreBySubject(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Integer> m = new LinkedHashMap<>();
        for (Pupil p : pupils) {
            for (Subject s : p.subjects()) {
                m.put(s.name(), m.getOrDefault(s.name(), 0) + s.score());
            }
        }
        for (String s : m.keySet()) {
            rsl.add(new Label(s, 1D * m.get(s) / pupils.size()));
        }
        return rsl;
    }

    public static Label bestStudent(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        for (Pupil p : pupils) {
            double sum = 0;
            for (Subject s : p.subjects()) {
                sum += s.score();
            }
            rsl.add(new Label(p.name(), sum));
        }
        rsl.sort(Comparator.naturalOrder());
        return rsl.get(rsl.size() - 1);
    }

    public static Label bestSubject(List<Pupil> pupils) {
        List<Label> rsl = new ArrayList<>();
        Map<String, Integer> m = new LinkedHashMap<>();
        for (Pupil p : pupils) {
            for (Subject s : p.subjects()) {
                m.put(s.name(), m.getOrDefault(s.name(), 0) + s.score());
            }
        }
        for (String s : m.keySet()) {
            rsl.add(new Label(s, m.get(s)));
        }
        rsl.sort(Comparator.naturalOrder());
        return rsl.get(rsl.size() - 1);
    }
}