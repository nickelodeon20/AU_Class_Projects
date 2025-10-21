#include <iostream>

int main () {
    int tests[10];
      int test;
      int num_elems;
      std::cout << "How many numbers?: "; 
      std::cin >> num_elems;
     for (int i = 0; i < num_elems; i++) {
             std::cout << "Please type a number: ";
             std::cin >> test;
             tests[i] = test;
        }
     return 0;
}