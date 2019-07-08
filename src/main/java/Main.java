import fizzBuzz.FizzBuzz;

public class Main {
    public static void main(String[] args) {

        // I wanted to make FizzBuzz more generics to accept not only Integer but T, but I don't know
        // how to pass that information about generics to static method builder, maybe it's too late,
        // I would need about 15-30 mins more, sorry good night
        FizzBuzz fizzBuzz = FizzBuzz.builder()
                //just configuration staff for printing
                .delimiter("-")
                .massDelimiter(System.lineSeparator())
                .displayValue(true) // display value of row
                .displayEmpty(true) //display row also if no rules is catch
                .sequenceGenerator(10, i -> i + 2) // this should be generic, but no time
                        //default generator is (1, i -> i + 1)
                //configuring own rules
                .moduloRule(3, "fizz") // (value % 3 == 0)
                .rule(value -> value % 5 == 0, "buzz") // general rule is for bigger complexity
                .build();

        String res = fizzBuzz.evaluateSequence(20); // continuously evaluate first 20 seq items according to config
        System.out.println(res);

        System.out.println("-----------------");

        res = fizzBuzz.evaluate(15); // evaluate fizzBuzz just for 15
        System.out.println(res);
    }
}
