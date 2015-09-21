package pl.net.lkd.simcheck;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SimTest {

    @Test
    public void testSim() {
        String s1 = "A quick brown fox jumps over the lazy dog";
        String s2 = "A lazy fox crawls behind the quick brown dog";

        Map<String, AbstractStringMetric> metrics = Collections.unmodifiableMap(Stream.of(
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Block distance", new BlockDistance()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Chapman lenght deviation", new ChapmanLengthDeviation()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Chapman mathicng soundex", new ChapmanMatchingSoundex()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Chapman mean length", new ChapmanMeanLength()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Chapman ordered name compound", new ChapmanOrderedNameCompoundSimilarity()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Cosine", new CosineSimilarity()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Dice", new DiceSimilarity()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Euclidean distance", new EuclideanDistance()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Jaccard", new JaccardSimilarity()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Jaro", new Jaro()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Jaro-Winkler", new JaroWinkler()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Levenshtein", new Levenshtein()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Matching Coefficient", new MatchingCoefficient()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Monge-Elkan", new MongeElkan()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Needleman-Wunch", new NeedlemanWunch()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Overlap coefficient", new OverlapCoefficient()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("QGrams", new QGramsDistance()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Smith-Waterman", new SmithWaterman()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Smith-Waterman-Gotoh", new SmithWatermanGotoh()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Smith-Waterman-Gotoh windowed affine", new SmithWatermanGotohWindowedAffine()),
              new AbstractMap.SimpleEntry<String, AbstractStringMetric>("Soundex", new Soundex()))
              .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

        for (Map.Entry<String, AbstractStringMetric> e : metrics.entrySet()) {
            String metric = e.getKey();
            float score = e.getValue().getSimilarity(s1, s2);
            log.info("{}: {}", metric, score);
        }
    }
}
