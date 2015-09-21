package pl.net.lkd.simcheck.resources

import lombok.extern.slf4j.Slf4j
import uk.ac.shef.wit.simmetrics.similaritymetrics.*
import uk.ac.shef.wit.simmetrics.tokenisers.InterfaceTokeniser

import javax.ws.rs.FormParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Slf4j
@Path("/process")
public class SimResource {

    @POST
    public Response process(@FormParam("s1") String s1,
                            @FormParam("s2") String s2,
                            @FormParam("tokeniser") String tokeniser) {
        try {
            InterfaceTokeniser tok = (InterfaceTokeniser) Class.forName("uk.ac.shef.wit.simmetrics.tokenisers." + tokeniser).newInstance()
            Map<String, AbstractStringMetric> metrics = [
                  "Block distance"                      : new BlockDistance(tok),
                  "Chapman length deviation"            : new ChapmanLengthDeviation(),
                  "Chapman matching soundex"            : new ChapmanMatchingSoundex(tok),
                  "Chapman mean length"                 : new ChapmanMeanLength(),
                  "Chapman ordered name compound"       : new ChapmanOrderedNameCompoundSimilarity(tok),
                  "Cosine"                              : new CosineSimilarity(tok),
                  "Dice"                                : new DiceSimilarity(tok),
                  "Euclidean distance"                  : new EuclideanDistance(tok),
                  "Jaccard"                             : new JaccardSimilarity(tok),
                  "Jaro"                                : new Jaro(),
                  "Jaro-Winkler"                        : new JaroWinkler(),
                  "Levenshtein"                         : new Levenshtein(),
                  "Matching Coefficient"                : new MatchingCoefficient(tok),
                  "Monge-Elkan"                         : new MongeElkan(tok),
                  "Needleman-Wunch"                     : new NeedlemanWunch(),
                  "Overlap coefficient"                 : new OverlapCoefficient(tok),
                  "QGrams"                              : new QGramsDistance(tok),
                  "Smith-Waterman"                      : new SmithWaterman(),
                  "Smith-Waterman-Gotoh"                : new SmithWatermanGotoh(),
                  "Smith-Waterman-Gotoh windowed affine": new SmithWatermanGotohWindowedAffine(),
                  "Soundex"                             : new Soundex()]
            Map<String, Float> results = metrics.collectEntries { k, v -> [(k): v.getSimilarity(s1, s2)] }
            return Response
                  .status(Response.Status.OK)
                  .type(MediaType.APPLICATION_JSON_TYPE)
                  .entity(results)
                  .build()
        } catch (InstantiationException e) {
            log.error("{}", e)
        } catch (IllegalAccessException e) {
            log.error("{}", e)
        } catch (ClassNotFoundException e) {
            log.error("{}", e)
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
    }

}