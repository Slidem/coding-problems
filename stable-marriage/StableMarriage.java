import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

/**
 * @author Alexandru Mihai
 */
public class StableMarriage {

    private final Map<String, LinkedHashSet<String>> guyPreferences;

    private final Map<String, LinkedHashSet<String>> galPreferences;

    private List<Match> matches;

    private int pairs;

    private StableMarriage(Map<String, String[]> guyPreferences, Map<String, String[]> galPreferences, int pairs) {
        this.guyPreferences = computePreference(guyPreferences);
        this.galPreferences = computePreference(galPreferences);
        this.matches = new ArrayList<>();
        this.pairs = pairs;
        compute();
    }

    // --------------------------------- helpers

    private void printMatches(){
        matches.forEach(m -> System.out.println("Match: " + m.guy + " " + m.gal));
    }

    private Map<String, LinkedHashSet<String>> computePreference(Map<String, String[]> guyPreferences) {
        return guyPreferences.entrySet().stream().collect(toMap(Map.Entry::getKey,e -> Arrays.stream(e.getValue()).collect(toCollection(LinkedHashSet::new))));
    }

    // --------------------------------- computation of matches

    private void compute() {
        compute(0, guyPreferences.keySet().iterator());
    }

    private void compute(int priority, Iterator<String> guysIterator) {
        if(pairs == 0){
            return;
        }

        if(!guysIterator.hasNext()){
            if(priority >= pairs){
                return;
            }
            compute(priority+1, guyPreferences.keySet().iterator());
            return;
        }

        String guy = guysIterator.next();
        String mostPreferedGal = getPersonBasedOnPriority(priority, guyPreferences.get(guy));
        String galsMostPreferdGuy = galPreferences.get(mostPreferedGal).iterator().next();
        if (Objects.equals(guy, galsMostPreferdGuy)) {
            //match !!
            matches.add(new Match(guy, mostPreferedGal));
            guyPreferences.remove(guy);
            galPreferences.remove(mostPreferedGal);
            guyPreferences.forEach((key, value) -> value.remove(mostPreferedGal));
            galPreferences.forEach((key, value) -> value.remove(guy));
            compute(0, guyPreferences.keySet().iterator());
        } else {
            compute(priority, guysIterator);
        }
    }

    private String getPersonBasedOnPriority(int priority, Collection<String> persons){
        int i = 0;
        Iterator<String> personsIterator = persons.iterator();
        String person = null;
        while (i <= priority && personsIterator.hasNext()) {
            person = personsIterator.next();
            i++;
        }
        return person;
    }

    // --------------------------------- helper class

    private static class Match{

        String guy;

        String gal;

        Match(String guy, String gal) {
            this.guy = guy;
            this.gal = gal;
        }
    }

    // --------------------------------- test

    public static void main(String[] args) {

        Map<String, String[]> guyPreferences = new HashMap<>();
        guyPreferences.put("andrew", new String[]{"caroline", "abigail", "maria", "betty"});
        guyPreferences.put("bill", new String[]{"caroline", "maria", "betty", "abigail"});
        guyPreferences.put("chester", new String[]{"betty", "caroline", "maria", "abigail"});
        guyPreferences.put("dan", new String[]{"betty", "caroline", "abigail", "maria"});

        Map<String, String[]> galPreferences = new HashMap<>();
        galPreferences.put("maria", new String[]{"andrew", "bill", "dan", "chester"});
        galPreferences.put("abigail", new String[]{"dan", "andrew", "bill", "chester"});
        galPreferences.put("betty", new String[]{"andrew", "bill", "dan", "chester"});
        galPreferences.put("caroline", new String[]{"chester", "bill", "andrew", "dan"});

        new StableMarriage(guyPreferences, galPreferences, 3).printMatches();
    }

}

