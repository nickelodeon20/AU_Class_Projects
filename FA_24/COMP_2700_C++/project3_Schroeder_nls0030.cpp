# include <iostream>
# include <cstdlib>
# include <assert.h>
# include <ctime>
# include <fstream>
# include <string>
# include <random>
# include <unordered_map>
# include <iomanip> // setprecision()
# include <sstream>
using namespace std;

////// Project 3 //////

// Nicholas Schroeder 
// Banner ID: 904328161
// project3_Schroeder_nls0030.cpp

/* 
*  I prompted Microsoft Copilot on functions to use in order to convert strings to doubles
*  in the check_values() function, as well as the idea for the try-catch block in order to 
*  easily determine whether the contents of the file are valid/invalid inputs. I asked GTA
*  Hai Vu about whether to use std::vector or large order_arrays as the data_order_array structure, and I 
*  decided on using vector after talking with him in Office Hours. I also checked with Microsoft Copilot 
*  my inFile operations for printing (since I was running into a bunch of errors) and utilized the 
*  "clear"/"seekg" functions to help fix those issues. I also Googled-searched ways to separate the string
*  read by getline(), and decided to utilize the stringstream(ss) to split it by "\t" and ".", as well as the 
*  emplace_back() function used in correctly add pairs to vectors. 
*/

vector<double> combined_order_array;
int item_counter = 0;
vector<int> order_array_of_sizes;
vector<pair<double, vector<int>>> data_order_array;


// Microsoft Copilot code for generating random 
// files (used to check code for errors through testing)
/*
std::vector<double> generateRandomNumbers(int K, double minValue, double maxValue) {
    std::vector<double> numbers;
    std::random_device rd; // Seed for the random number engine
    std::mt19937 gen(rd()); // Mersenne Twister engine
    std::uniform_real_distribution<> dis(minValue, maxValue); // Distribution range

    for (int i = 0; i < K; ++i) {
        numbers.push_back(dis(gen));
    }

    return numbers;
}
void writeNumbersToFile(const std::vector<double>& numbers, const std::string& filename) {
    std::ofstream outFile(filename);

    if (outFile.is_open()) {
        for (const auto& num : numbers) {
            outFile << std::fixed << std::setprecision(2) << num << std::endl;
        }
        outFile.close();
    } else {
        std::cerr << "Unable to open file";
    }
}
*/
/////////////////////////
// Valid input checking function
/////////////////////////

bool check_values(std::ifstream &newFile) {
    string line;
    double num;
    int file_size = 0;

    while (std::getline(newFile, line)) {
        try {
        // -- Convert string "line" that is read to double value
            num = std::stod(line);

        // 2) Invalid file --> Invalid Values (anything non-decimal)
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid input '" << e.what() << "' is not a valid input. The input file is invalid.\n";
            return false;
        }
        // If valid number then store in larger order_array
        combined_order_array.push_back(num);
        item_counter++;
    }
    // Store size of each file in another order_array for printing purposes
    order_array_of_sizes.push_back(item_counter);
    item_counter = 0;

    // T if no values checked are invalid
    return true;
}
bool check_values_chronologically(std::ifstream &newFile) {
    string line;
    double num;
    int file_size = 0;
    int index_counter = 0;
    // Even index = data_order_array #'s, Odd index = timestamp
    vector<int> timestamp;

    while (std::getline(newFile, line)) {
        try {
            // -- Convert string "line" that is read to double value and timestamp
            stringstream ss(line);
            string number = ""; 

            // Split line by tab character
            while (getline(ss, number, '\t')) {
                // Add new empty pair to replace with values later
                if (index_counter >= data_order_array.size()) {
                    data_order_array.emplace_back(0.0, timestamp);
                }
                // Check with item counter if its a timestamp or data_order_array input
                if (item_counter % 2 == 0) {
                    // If data_order_array --> add to set
                    num = std::stod(number);
                    data_order_array[index_counter].first = num;
                } else {
                    stringstream nn(number);
                    string time = "";
                    while (getline(nn, time, '.')) {
                        try {
                            num = stod(time);
                            data_order_array[index_counter].second.push_back(num);
                        } catch (const std::invalid_argument& e) {
                            std::cerr << "Invalid input '" << e.what() << "' is not a valid input. The input file is invalid.\n";
                            return false;
                        }
                    }
                }
                item_counter++;
            }        
        // 2) Invalid file --> Invalid Values (anything non-decimal)
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid input '" << e.what() << "' is not a valid input. The input file is invalid.\n";
            return false;
        }
        index_counter++;
    }
    // Store size of each file in another order_array for printing purposes
    order_array_of_sizes.push_back(index_counter);
    item_counter = 0;
    index_counter = 0;
    // T if no values checked are invalid
    return true;
}

/////////////////////////
// Size function
/////////////////////////
int list_size () {
    return combined_order_array.size();
}

/////////////////////////
// Sorting function     
/////////////////////////

// Function called to swap values of order_array - used in Sorting function
void swap_values (int min, int val, vector<double>& sorted_order_array) {
    // Extra check to make sure indexes are correct
    double temp = sorted_order_array[min];
    sorted_order_array[min] = sorted_order_array[val];
    sorted_order_array[val] = temp;
}

void swap_values_chronologically(int min, int val, vector<pair<double, vector<int>>> data_order_array) {
    pair<double, vector<int>> temp = data_order_array[min];
    data_order_array[min] = data_order_array[val];
    data_order_array[val] = temp;
}

// Main Sorting function
vector<double> sort_values(vector<double> combined_order_array, int size) {
    vector<double> sorted_order_array = combined_order_array;

    int i = 0; // 1st vector counter
    int j = 0; // 2nd vector counter

    // if size < 2, then order_array does not need to be sorted
    if (size >= 2) {

        // Selection Sort implementation
        for (i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (j = i + 1 ; j < size; j++) {
                int currentIndex = j;
                if (sorted_order_array[currentIndex] < sorted_order_array[minIndex]) {
                    minIndex = currentIndex;
                }
            }
            // swap min value with current index
            swap_values(minIndex, i, sorted_order_array);
        }
        // if size = 2, check only 2 elements
    }
    return sorted_order_array;
}

vector<double> sort_values_chronologically(vector<pair<double, vector<int>>> data_order_array, int size) {
    vector<pair<double, vector<int>>> sorted_data_order_array = data_order_array;
    vector<double> sorted_data_order_array_vector;
    int i = 0; // 1st vector counter
    int j = 0; // 2nd vector counter

    // if size < 2, then order_array does not need to be sorted
    if (size >= 2) {

        // Selection Sort implementation
        for (i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (j = i + 1 ; j < size; j++) {
                int currentIndex = j;
                // Current Hours < Min Hours
                if (sorted_data_order_array[currentIndex].second[0] < sorted_data_order_array[minIndex].second[0]) {
                    minIndex = currentIndex;
                // Current Hours = Min Hours
                } else if (sorted_data_order_array[currentIndex].second[0] == sorted_data_order_array[minIndex].second[0]) {
                    // Current Mins < Min Mins
                    if (sorted_data_order_array[currentIndex].second[1] < sorted_data_order_array[minIndex].second[1]) {
                        minIndex = currentIndex;
                    // Current Mins = Min Mins
                    } else if (sorted_data_order_array[currentIndex].second[1] == sorted_data_order_array[minIndex].second[1]) {
                        // Current Sec < Min Sec
                        if (sorted_data_order_array[currentIndex].second[2] < sorted_data_order_array[minIndex].second[2]) {
                            minIndex = currentIndex;
                        }
                    }
                }
            }
            // swap min value with current index
            swap_values_chronologically(minIndex, i, sorted_data_order_array);
        }
        // if size = 2, check only 2 elements
    }
    for (int i = 0; i < sorted_data_order_array.size(); i++) {
        sorted_data_order_array_vector.push_back(sorted_data_order_array[i].first);
    }
    return sorted_data_order_array_vector;
}

/////////////////////////
// data_order_array Statistics Functions
/////////////////////////
double mean (int size, vector<double> order_array) {
    double sum = 0.0;

    for (int i = 0; i < size; i++) {
        sum += order_array[i];
    }
    return sum / size;
}

double median (int size, vector<double> order_array) {
    if (size % 2 == 1) {
        int mid = size / 2;
        return order_array[mid];
    } else {
        int mid = size / 2;
        double median = (order_array[mid] + order_array[mid - 1]) / 2.0;
        return median;
    }

}

double mode (int size, vector<double> order_array) {
    unordered_map<double, int> map;

    for (int i = 0; i < size; i++) {
        map[order_array[i]]++;
    }
    vector<double> manyModes;
    int maxCount = 0;

    for (const auto pair : map) {
        if (pair.second > maxCount) {
            maxCount = pair.second;
        }
    }
    for (const auto pair : map) {
        if (pair.second == maxCount) {
            manyModes.push_back(pair.first); 
        }
    }

    if (manyModes.size() > 1) {
        double sum = 0;
        for (int i = 0; i < manyModes.size(); i++) {
            sum += manyModes[i];
        }
        return sum / manyModes.size();
    }
    return manyModes[0];
}

/////////////////////////
// Output, Printing functions
/////////////////////////
void print_values(const string &fileName) {
    ifstream newFile(fileName);
    string line;
    while (std::getline(newFile, line)) {
        cout << line << "\n";
    }
    newFile.close();
}

string make_output_file (vector<double> order_array, vector<double> chrono_array, double mean_val, double median_val, double mode_val, string type) {
    ofstream outFile("output_phase1.txt");
    if (!outFile) {
        cout << "Cannot open file.\n";
        return "";
    }
    if (type == "both") {
        outFile << "*** Summarized Statistics ***\n";
        outFile << "The orderly sorted list of " << order_array.size() << " is: \n";
        outFile << order_array[0];
        for (int i = 1; i < order_array.size(); i++) {
            outFile << ", " << order_array[i];
        }
        outFile << "\n" << "\n"; 
        outFile << "The mean is " << mean_val << "\n";
        outFile << "The median is " << median_val << "\n";
        outFile << "The mode is " << mode_val << "\n";

        outFile << "The chronologically sorted list of " << chrono_array.size() << " is: \n";
        for (int i = 1; i < chrono_array.size(); i++) {
            outFile << ", " << chrono_array[i];
        }
        outFile << "The mean is " << mean(chrono_array.size(), chrono_array) << "\n";
        outFile << "The median is " << median(chrono_array.size(), chrono_array) << "\n";
        outFile << "The mode is " << mode(chrono_array.size(), chrono_array) << "\n";

        return "output_phase1.txt";


    } else if (type == "order") {
        outFile << "*** Summarized Statistics ***\n";
        outFile << "The orderly sorted list of " << order_array.size() << " is: \n";
        outFile << order_array[0];
        for (int i = 1; i < order_array.size(); i++) {
            outFile << ", " << order_array[i];
        }
        outFile << "\n" << "\n"; 
        outFile << "The mean is " << mean_val << "\n";
        outFile << "The median is " << median_val << "\n";
        outFile << "The mode is " << mode_val << "\n";
        return "output_phase1.txt";

    } else {
        outFile << "*** Summarized Statistics ***\n";
        outFile << "The chronologically sorted list of " << chrono_array.size() << " is: \n";
        outFile << order_array[0];
        for (int i = 1; i < order_array.size(); i++) {
            outFile << ", " << order_array[i];
        }
        outFile << "The mean is " << mean(chrono_array.size(), chrono_array) << "\n";
        outFile << "The median is " << median(chrono_array.size(), chrono_array) << "\n";
        outFile << "The mode is " << mode(chrono_array.size(), chrono_array) << "\n";
        return "output_phase1.txt";
    }
}
////////////////////////////
//  Main body of program  //
////////////////////////////
int main () {
    int file_count;
    int file_num = 1; // n files
    string fileName;
    string type;
    cout << "*** Welcome to Nick's data_order_array Analyzer ***\n";
    cout << "Enter the number of files to read: ";
    cin >> file_count;

    /////////////////////////////////////////////////
    /////////////////////////////////////////////////
    /*
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(1, 9); // Random number of elements between 1 and 9

    int count = dis(gen);
    double minValue = -10.0; // Minimum value for random numbers
    double maxValue = 10.0; // Maximum value for random numbers
    vector<string> fileNames = {"one.txt", "two.txt", "three.txt", "four.txt", "five.txt", "six.txt", 
                                "seven.txt", "eight.txt", "nine.txt"};
    for (int i = 0; i < file_count; ++i) {
        int count = dis(gen);
        std::vector<double> randomNumbers = generateRandomNumbers(count, minValue, maxValue);
        writeNumbersToFile(randomNumbers, fileNames[i]);
    }*/
    /////////////////////////////////////////////////
    /////////////////////////////////////////////////

    // while files to be read > 0
    while (file_num <= file_count) {

        cout << "Enter the filename for file " << file_num << ": ";
        cin >> fileName;

        // Break the file reading loop if "quit"
        if (fileName == "quit") {
            cout << "Input cancelled - proceeding to calculations...\n" << "\n";
            break;
        }
        ifstream inFile;

        // 1) Check for non-existing file
        inFile.open(fileName, std::ios::in);
        if (!inFile.is_open()) {
            // Returns to top code 
            cout << "Cannot open a non-existing file. Please try again.\n";
            continue;
        }
        // Read file function
        // 2) Check for valid values entire way through file
        if (type == "both") {
            if (!check_values(inFile)) {
                cout << "File contains invalid values. Please try again.\n";
                inFile.close();
                inFile.clear();
            inFile.seekg(0, ios::beg);
            } else if (!check_values_chronologically(inFile)) {
                cout << "File contains invalid values. Please try again.\n";
                inFile.close();
                continue;
            }
        } else if (type == "time") {
            if (!check_values_chronologically(inFile)) {
                cout << "File contains invalid values. Please try again.\n";
                inFile.close();
                continue;
            }
        } else {
            if (!check_values(inFile)) {
            cout << "File contains invalid values. Please try again.\n";
            inFile.close();
            continue;
            }
        }
        // 3) Valid file
        //      -- Rewind file to beginning
        inFile.clear();
        inFile.seekg(0, ios::beg);
        //      -- Print all values in file
        cout << "The list of " << order_array_of_sizes[order_array_of_sizes.size()-1] << " in file " << fileName << " is: \n";
        print_values(fileName);
        cout << "\n";

        inFile.close();
        file_num += 1;
    }

    if (file_num == 1) {
        cout << "*** Goodbye. ***";
    } else {
        string type;
        cout << "Sort by order, time, or both: ";
        cin >> type;
        cout << "\n";

        double mean_val = 0.0;
        double mode_val = 0.0;
        double median_val = 0.0;

        //////////////
        // Statistic Printing
        //////////////
        // sort_values(combined_order_array) --> outputs sorted_order_array, 
        // which is then used in print_output_values()
        vector<double> sorted_order_array = sort_values(combined_order_array, combined_order_array.size());
        vector<double> chrono_sorted_order_array = sort_values_chronologically(data_order_array, data_order_array.size());
        string outFileName = "";
       
        if (type == "both")
        {
            double mean_val = mean(combined_order_array.size(), sorted_order_array);
            double median_val = median(combined_order_array.size(), sorted_order_array);
            double mode_val = mode(combined_order_array.size(), sorted_order_array);
            sorted_order_array = chrono_sorted_order_array;
            // Sorted order_array is the chronologically sorted order_array
            string outFileName = make_output_file(sorted_order_array, chrono_sorted_order_array, mean_val, median_val, mode_val, type);
                    print_values(outFileName);

        }
        else if (type == "order")
        {
            double mean_val = mean(combined_order_array.size(), sorted_order_array);
            double median_val = median(combined_order_array.size(), sorted_order_array);
            double mode_val = mode(combined_order_array.size(), sorted_order_array);
            string outFileName = make_output_file(sorted_order_array, chrono_sorted_order_array, mean_val, median_val, mode_val, type);
            
                    print_values(outFileName);
}
        else
        {
            double mean_val = mean(chrono_sorted_order_array.size(), chrono_sorted_order_array);
            double median_val = median(chrono_sorted_order_array.size(), chrono_sorted_order_array);
            double mode_val = mode(chrono_sorted_order_array.size(), chrono_sorted_order_array);
            string outFileName = make_output_file(chrono_sorted_order_array, chrono_sorted_order_array, mean_val, median_val, mode_val, type);
            print_values(outFileName);

        }
    }
}
