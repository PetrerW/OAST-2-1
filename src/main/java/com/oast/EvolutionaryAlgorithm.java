package com.oast;

public class EvolutionaryAlgorithm {

    // parametry algorytmu
    int population = 1000;
    const table_length = 1000;
    float p = 0.5;
    float p_cross = 0.25;
    float p_mutate = 0.01;
    const number_of_generations = 100;
    const max_cross_number = 200000;
    const max_mutation_number = 1000000;
    const max_no_change_generations = 25000;
    const max_time = 300;//czas w [s]
    int cross_number = 0;
    int mutation_number = 0;
    int no_change_generations = 0;
    int number_of_continuous_non_better_solutions = 10;

    Date start_time = new Date().getSeconds();
    Date current_time = new Date().getSeconds();

    int number_of_chromosomes = 10000;


    //kryterium stopu
    while ((current_time - start_time <= max_time) && (no_change_generations != max_no_change_generations) && (cross_number < max_cross_number) && (mutation_number < max_mutation_number))
    {

    }
}
