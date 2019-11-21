package br.ufrpe.wanderlustapp.infra.recomendacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrpe.wanderlustapp.pratoTipico.dominio.PratoTipico;
import br.ufrpe.wanderlustapp.usuario.dominio.Usuario;

public class SlopeOne {
    private static Map<PratoTipico, Map<PratoTipico, Integer>> diff = new HashMap<>();
    private static Map<PratoTipico, Map<PratoTipico, Integer>> freq = new HashMap<>();
    private static Map<Usuario, HashMap<PratoTipico, Integer>> inputData;
    private static Map<Usuario, HashMap<PratoTipico, Integer>> outputData = new HashMap<>();
    private static List<PratoTipico> listaRestaurantes;
    private static Recomendacao recomendacao;

    public static Map<Usuario, HashMap<PratoTipico, Integer>> slopeOne(Map<Usuario, HashMap<PratoTipico, Integer>> matrizCliente, List<PratoTipico> restaurantes) {
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
    private static void buildDifferencesMatrix(Map<Usuario, HashMap<PratoTipico, Integer>> data) {
        for (HashMap<PratoTipico, Integer> cliente : data.values()) {
            for (Map.Entry<PratoTipico, Integer> e : cliente.entrySet()) {
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<PratoTipico, Integer>());
                    freq.put(e.getKey(), new HashMap<PratoTipico, Integer>());
                }
                for (Map.Entry<PratoTipico, Integer> e2 : cliente.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    int oldDiff = 0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).floatValue();
                    }
                    int observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (PratoTipico j : diff.keySet()) {
            for (PratoTipico i : diff.get(j).keySet()) {
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
    private static Map<Usuario, HashMap<PratoTipico, Float>> predict(Map<Usuario, HashMap<PratoTipico, Float>> data) {
        HashMap<PratoTipico, Float> uPred = new HashMap<PratoTipico, Float>();
        HashMap<PratoTipico, Integer> uFreq = new HashMap<PratoTipico, Integer>();
        for (PratoTipico j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0f);
        }
        for (Map.Entry<Usuario, HashMap<PratoTipico, Float>> e : data.entrySet()) {
            for (PratoTipico j : e.getValue().keySet()) {
                for (PratoTipico k : diff.keySet()) {
                    try {
                        float predictedValue = diff.get(k).get(j).floatValue() + e.getValue().get(j).floatValue();
                        float finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<PratoTipico, Float> clean = new HashMap<PratoTipico, Float>();
            for (PratoTipico j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).floatValue() / uFreq.get(j).intValue());
                }
            }
            for (PratoTipico j : listaRestaurantes) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                }
            }
            outputData.put(e.getKey(), clean);
        }
        return outputData;
    }
}
