package amata1219.walkure.spigot.data.processor;

import amata1219.walkure.spigot.data.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MapSplitter {

    public static <K, V> Pair<HashMap<K, V>, HashMap<K, V>> split(Map<K, V> map, Predicate<K> condition) {
        HashMap<K, V> left = new HashMap<>(), right = new HashMap<>();
        map.forEach((key, value) -> (condition.test(key) ? right : left).put(key, value));
        return new Pair<>(left, right);
    }
}
