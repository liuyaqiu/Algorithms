public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        String bestcast = "";
        int bestdist = 0;
        for(int i = 0; i < nouns.length; i++) {
            int current_dist = 0;
            for(int j = 0; j < nouns.length; j++) {
                current_dist += wordnet.distance(nouns[i], nouns[j]);
            }
            if(bestdist < current_dist) {
                bestdist = current_dist;
                bestcast = nouns[i];
            }
        }
        return bestcast;
    }

    public static void main(String[] args) {
    }
}