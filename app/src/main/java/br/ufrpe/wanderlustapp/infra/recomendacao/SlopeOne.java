package br.ufrpe.wanderlustapp.infra.recomendacao;

import java.util.HashMap;
import java.util.Map;

public class SlopeOne {
    private static Map<Restaurante, Map<Restaurante, Float>> diff = new HashMap<>();
    private static Map<Restaurante, Map<Restaurante, Integer>> freq = new HashMap<>();
    private static Map<Cliente, HashMap<Restaurante, Float>> inputData;
    private static Map<Cliente, HashMap<Restaurante, Float>> outputData = new HashMap<>();
    private static List<Restaurante> listaRestaurantes;
    private static Recomendacao recomendacao;

    public static Map<Cliente, HashMap<Restaurante, Float>> slopeOne(Map<Cliente, HashMap<Restaurante, Float>> matrizCliente, List<Restaurante> restaurantes) {
        inputData = matrizCliente;
        listaRestaurantes = restaurantes;
        buildDifferencesMatrix(inputData);
        predict(inputData);
        return outputData;
    }

    /**
     * Based on the available data, calculate the relationships between the
     * items and number of occurences
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static void buildDifferencesMatrix(Map<Cliente, HashMap<Restaurante, Float>> data) {
        for (HashMap<Restaurante, Float> cliente : data.values()) {
            for (Map.Entry<Restaurante, Float> e : cliente.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Restaurante, Float>());
                    freq.put(e.getKey(), new HashMap<Restaurante, Integer>());
                }
                for (Map.Entry<Restaurante, Float> e2 : cliente.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    float oldDiff = 0.0f;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).floatValue();
                    }
                    float observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Restaurante j : diff.keySet()) {
            for (Restaurante i : diff.get(j).keySet()) {
                float oldValue = diff.get(j).get(i).floatValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data
     *            existing user data and their items' ratings
     */
    private static Map<Cliente, HashMap<Restaurante, Float>> predict(Map<Cliente, HashMap<Restaurante, Float>> data) {
        HashMap<Restaurante, Float> uPred = new HashMap<Restaurante, Float>();
        HashMap<Restaurante, Integer> uFreq = new HashMap<Restaurante, Integer>();
        for (Restaurante j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0f);
        }
        for (Map.Entry<Cliente, HashMap<Restaurante, Float>> e : data.entrySet()) {
            for (Restaurante j : e.getValue().keySet()) {
                for (Restaurante k : diff.keySet()) {
                    try {
                        float predictedValue = diff.get(k).get(j).floatValue() + e.getValue().get(j).floatValue();
                        float finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Restaurante, Float> clean = new HashMap<Restaurante, Float>();
            for (Restaurante j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).floatValue() / uFreq.get(j).intValue());
                }
            }
            for (Restaurante j : listaRestaurantes) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                }
            }
            outputData.put(e.getKey(), clean);
        }
        return outputData;
    }
}
