import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Species {
    private final int[] predators;
    private final int expected;

    public Species(int[] predators, int expected) {
        this.predators = predators;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {new int[]{-1, 0, 1}, 3},
                {new int[]{1, -1, 3, -1}, 2},
                {new int[]{-1,0,0,0,1,1,4}, 3}
        });
    }

    @Test
    public void SpeciesTest() {
        int minGroups = GetMinGroupsForSpeciesWithBruteForce(predators);
        assertEquals(expected, minGroups);
    }

    @Test
    public void SpeciesTestEfficient() {
        int minGroups = GetMinGroupsForSpeciesWithBruteForce(predators);
        assertEquals(expected, minGroups);
    }

    private int GetMinGroupsForSpeciesWithBruteForce(int[] predators) {
        ArrayList<Set<Integer>> groups = new ArrayList<>();
        ArrayList<Set<Integer>> predatorPreyList = new ArrayList<>();
        for (int i = 0; i < predators.length; i++) {
            predatorPreyList.add(new HashSet<>());
        }

        groups.add(new HashSet());
        boolean added;
        for (int i = 0; i < predators.length; i++) {
            added = false;
            int directPredator = predators[i];
            int indirectPredator = directPredator == -1 ? -1 : predators[directPredator];

            if (directPredator != -1)
                predatorPreyList.get(directPredator).add(i);
            if (indirectPredator != -1)
                predatorPreyList.get(indirectPredator).add(i);

            for (Set<Integer> group : groups) {
                Set<Integer> preySet = predatorPreyList.get(i);
                if (group.contains(directPredator) || group.contains(indirectPredator) || ContainsAny(preySet, group)) {
                    continue;
                } else {
                    group.add(i);
                    added = true;
                    break;
                }
            }

            if (!added) {
                Set<Integer> newGroup = new HashSet<>();
                newGroup.add(i);
                groups.add(newGroup);
            }
        }

        return groups.size();
    }

    private boolean ContainsAny(Set<Integer> prey, Set<Integer> group) {
        if (prey.isEmpty() || group.isEmpty())
            return false;
        for (Integer species : prey) {
            if (group.contains(species))
                return true;
        }
        return false;
    }
}
