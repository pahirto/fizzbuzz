package fizzBuzz;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sorry I will not write javadocs for this class. I know that some methods would need it, by good night
 * */
public class FizzBuzz {

    private List<FizzBuzzRule> rules;
    private String delimiter;
    private String massDelimiter;
    private boolean displayValue;
    private boolean displayEmpty;

    private int sequenceInitValue;
    private UnaryOperator<Integer> sequenceOperator;

    private FizzBuzz(List<FizzBuzzRule> rules, String delimiter, String massDelimiter, boolean displayValue, boolean displayEmpty, int sequenceInitValue, UnaryOperator<Integer> sequenceOperator) {
        this.rules = rules;
        this.delimiter = delimiter;
        this.massDelimiter = massDelimiter;
        this.displayValue = displayValue;
        this.displayEmpty = displayEmpty;
        this.sequenceInitValue = sequenceInitValue;
        this.sequenceOperator = sequenceOperator;
    }

    public static FizzBuzzBuilder builder(){
        return new FizzBuzzBuilder();
    }

    public String evaluate(int param){
        StringBuilder sb = new StringBuilder();
        String sequence = rules.stream()
                .filter((rule) -> rule.getPredicate().test(param))
                .map(FizzBuzzRule::getFizzBuzzWord)
                .collect(Collectors.joining(delimiter));

        if(displayValue && (displayEmpty || !sequence.isEmpty())){
            sb.append(param).append(" -> ");
        }

        if(sequence.isEmpty()) {
            sb.append(param);
        } else {
            sb.append(sequence);
        }

        return sb.toString();
    }

    public String evaluateSequence(int count){
        return Stream.iterate(sequenceInitValue, sequenceOperator)
                .limit(count)
                .map(this::evaluate)
                .filter(string -> displayEmpty || !string.isEmpty())
                .collect(Collectors.joining(massDelimiter));
    }

    public static class FizzBuzzBuilder{

        private List<FizzBuzzRule> rules = new ArrayList<>();
        private String delimiter = "";
        private String massDelimiter = ", ";
        private boolean displayValue = false;
        private boolean displayEmpty = false;

        private int sequenceInitValue = 1;
        private UnaryOperator<Integer> sequenceOperator = i -> i + 1;

        public FizzBuzzBuilder moduloRule(int moduloValue, String fizzBuzzWord){
            rules.add(new FizzBuzzRule(val -> val % moduloValue == 0, fizzBuzzWord));
            return this;
        }

        public FizzBuzzBuilder rule(Predicate<Integer> predicate, String fizzBuzzWord){
            rules.add(new FizzBuzzRule(predicate, fizzBuzzWord));
            return this;
        }

        public FizzBuzzBuilder sequenceGenerator(int sequenceInitValue, UnaryOperator<Integer> sequenceOperator){
            this.sequenceInitValue = sequenceInitValue;
            this.sequenceOperator = sequenceOperator;
            return this;
        }

        public FizzBuzzBuilder displayValue(boolean displayValue){
            this.displayValue = displayValue;
            return this;
        }

        public FizzBuzzBuilder displayEmpty(boolean displayEmpty){
            this.displayEmpty = displayEmpty;
            return this;
        }

        public FizzBuzzBuilder delimiter(String delimiter){
            this.delimiter = delimiter;
            return this;
        }

        public FizzBuzzBuilder massDelimiter(String massDelimiter){
            this.massDelimiter = massDelimiter;
            return this;
        }

        public FizzBuzz build(){
            return new FizzBuzz(rules, delimiter, massDelimiter, displayValue, displayEmpty, sequenceInitValue, sequenceOperator);
        }
    }

    private static class FizzBuzzRule {
        private Predicate<Integer> predicate;
        private String fizzBuzzWord;

        FizzBuzzRule(Predicate<Integer> predicate, String fizzBuzzWord) {
            this.predicate = predicate;
            this.fizzBuzzWord = fizzBuzzWord;
        }

        Predicate<Integer> getPredicate() {
            return predicate;
        }

        String getFizzBuzzWord() {
            return fizzBuzzWord;
        }
    }
}
