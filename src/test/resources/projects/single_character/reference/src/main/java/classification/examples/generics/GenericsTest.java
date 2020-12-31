package classification.examples.generics;

import classification.data.BinaryLabel;
import classification.data.Dataset;
import classification.data.SupervisedSample;
import classification.models.BinaryClassifier;

/**
 * @author Kim Berninger
 * @version 1.1.0
 */
class GenericsTest {
    static BinaryClassifier<Animal> animalClassifier;
    static Dataset<SupervisedSample<Animal, BinaryLabel>> trainAnimals;
    static Dataset<Animal> testAnimals;

    static BinaryClassifier<Car> carClassifier;
    static Dataset<SupervisedSample<Car, BinaryLabel>> trainCars;
    static Dataset<Car> testCars;

    static BinaryClassifier<Dog> dogClassifier;
    static Dataset<SupervisedSample<Dog, BinaryLabel>> trainDogs;
    static Dataset<Dog> testDogs;

    static BinaryClassifier<Poodle> poodleClassifier;
    static Dataset<SupervisedSample<Poodle, BinaryLabel>> trainPoodles;
    static Dataset<Poodle> testPoodles;

    static Dataset<SupervisedSample<Animal, Age>> nonBinaryAnimals;
    static Dataset<SupervisedSample<Dog, Age>> nonBinaryDogs;
    static Dataset<SupervisedSample<Car, Age>> nonBinaryCars;
    
    /*
     * Jede der Zeilen in dieser Methode sollte problemlos ohne Fehler oder
     * Warnung durch den Compiler gehen.
     */
    static void shouldWork() {
        @SuppressWarnings("unused")
        Dataset<Cat> testCats;

        animalClassifier.fit(trainAnimals, 1);
        animalClassifier.fit(trainDogs, 1);
        animalClassifier.fit(trainPoodles, 1);

        carClassifier.fit(trainCars, 1);
        dogClassifier.fit(trainDogs, 1);
        dogClassifier.fit(trainPoodles, 1);
        poodleClassifier.fit(trainPoodles, 1);

        animalClassifier.evaluate(trainAnimals);
        animalClassifier.evaluate(trainDogs);
        animalClassifier.evaluate(trainPoodles);

        carClassifier.evaluate(trainCars);
        dogClassifier.evaluate(trainDogs);
        dogClassifier.evaluate(trainPoodles);
        poodleClassifier.evaluate(trainPoodles);

        animalClassifier.predict(trainAnimals);
        animalClassifier.predict(testAnimals);
        animalClassifier.predict(trainDogs);
        animalClassifier.predict(testDogs);
        animalClassifier.predict(trainPoodles);
        animalClassifier.predict(testPoodles);

        carClassifier.predict(trainCars);
        carClassifier.predict(testCars);

        dogClassifier.predict(trainDogs);
        dogClassifier.predict(testDogs);
        dogClassifier.predict(trainPoodles);
        dogClassifier.predict(testPoodles);

        poodleClassifier.predict(trainPoodles);
        poodleClassifier.predict(testPoodles);

        animalClassifier.predict(nonBinaryAnimals);
        animalClassifier.predict(nonBinaryDogs);

        dogClassifier.predict(nonBinaryDogs);

        carClassifier.predict(nonBinaryCars);
    }
    
    /*
     * Jede einzelne Zeile dieser Methode sollte einen Compilerfehler
     * hervorrufen. Gehen Sie sicher, dass diese Anforderung erfüllt ist, indem
     * Sie jede Zeile in dieser Methode einzeln einkommentieren und
     * sicherstellen, dass der Code daraufhin nicht mehr vom Compiler akzeptiert
     * wird. 
     */
    public static void shouldNotWork() {
        // BinaryClassifier<Cat> catClassifier;
        // Dataset<SupervisedSample<Cat, BinaryLabel>> trainCats;
        
        // BinaryClassifier<Vehicle> vehicleClassifier;
        // Dataset<SupervisedSample<Vehicle, BinaryLabel>> trainVehicles;
        // Dataset<Vehicle> testVehicles;
        
        // animalClassifier.fit(testAnimals, 1);
        // animalClassifier.fit(trainCars, 1);
        
        // dogClassifier.fit(trainAnimals, 1);
        // dogClassifier.fit(testDogs, 1);
        
        // poodleClassifier.fit(trainAnimals, 1);
        // poodleClassifier.fit(trainDogs, 1);
        // poodleClassifier.fit(testPoodles, 1);
        
        // animalClassifier.evaluate(testAnimals);
        // animalClassifier.evaluate(trainCars);
        
        // dogClassifier.evaluate(trainAnimals);
        // dogClassifier.evaluate(testDogs);
        
        // poodleClassifier.evaluate(trainAnimals);
        // poodleClassifier.evaluate(trainDogs);
        // poodleClassifier.evaluate(testPoodles);
        
        // animalClassifier.predict(testCars);

        // animalClassifier.fit(nonBinaryAnimals, 1);
        // animalClassifier.fit(nonBinaryDogs, 1);
        // animalClassifier.fit(nonBinaryCars, 1);

        // dogClassifier.fit(nonBinaryAnimals, 1);
        // dogClassifier.fit(nonBinaryDogs, 1);
        // dogClassifier.fit(nonBinaryCars, 1);

        // poodleClassifier.fit(nonBinaryAnimals, 1);
        // poodleClassifier.fit(nonBinaryDogs, 1);
        // poodleClassifier.fit(nonBinaryCars, 1);

        // carClassifier.fit(nonBinaryAnimals, 1);
        // carClassifier.fit(nonBinaryDogs, 1);
        // carClassifier.fit(nonBinaryCars, 1);

        // animalClassifier.evaluate(nonBinaryAnimals);
        // animalClassifier.evaluate(nonBinaryDogs);
        // animalClassifier.evaluate(nonBinaryCars);

        // dogClassifier.evaluate(nonBinaryAnimals);
        // dogClassifier.evaluate(nonBinaryDogs);
        // dogClassifier.evaluate(nonBinaryCars);

        // poodleClassifier.evaluate(nonBinaryAnimals);
        // poodleClassifier.evaluate(nonBinaryDogs);
        // poodleClassifier.evaluate(nonBinaryCars);

        // carClassifier.evaluate(nonBinaryAnimals);
        // carClassifier.evaluate(nonBinaryDogs);
        // carClassifier.evaluate(nonBinaryCars);
        
        // animalClassifier.predict(nonBinaryCars);

        // dogClassifier.predict(nonBinaryCars);

        // poodleClassifier.predict(nonBinaryCars);

        // carClassifier.predict(nonBinaryAnimals);
        // carClassifier.predict(nonBinaryDogs);
    }
}
