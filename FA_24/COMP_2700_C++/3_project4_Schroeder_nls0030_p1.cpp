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
# include <cctype>
# include <algorithm>
using namespace std;

////// Project 4 //////

// Nicholas Schroeder 
// Banner ID: 904328161
// project4_Schroeder_nls0030.cpp

/*  
I prompted Microsoft Copilot for a function that would convert strings
into lowercase copies. 
*/

class Node {
// to be accessed from anywhere in program
public:
    string question;
    string answer;
    double points;
    int q_number;
    Node* previous;
    Node* next;

    // Constructor for Questions
    Node(string q, string a, int q_num, double pts, Node *p, Node *n)
    {
        question = q;
        answer = a;
        q_number = q_num;
        points = pts;
        previous = p;
        next = n;
    }
    // Constructor for MCQ Answers
    Node(string a, Node* p, Node* n) {
        answer = a;
        previous = p;
        next = n;
    }
};  

// Convert any string to uppercase 
string to_lowercase(const string& str) {
    string newString = str;
    std::transform(newString.begin(), newString.end(), newString.begin(), ::tolower);
    return newString;
}

// Check if input is valid
bool check_point_value(double val) {
    // Only acceptable point values are #'s > 0
    if (val > 0.0) {
        return true;
    }
    return false;
}
// Checks that MCQ input answer actually exists in LinkedList of options
bool check_valid_mcq_answer(Node* answer_head, const string& user_answer) {
    Node* current = answer_head;
    char option = 'A';

    while (current != nullptr) {
        if (user_answer == string(1, option)) {
            return true;
        }
        current = current->next;
        option++;
    }
    return false;
}
// Checks question type string
bool check_valid_q_type(string q) {
    // Only 3 valid question inputs
    string q_lower = to_lowercase(q);
    if (q_lower == "mcq" || q_lower == "tf" || q_lower == "wr") {
        return true;
    }
    return false;
}

// All valid inputs of any case combination of "true"/"false"
bool check_valid_tf(string a) {
    string a_lower = to_lowercase(a);

    if (a_lower == "true" || a_lower == "false") {
        return true;
    }
    return false;
}

// Prints out mcq options for assessment phase
void print_mcq_options(Node* answer_head) {
    Node* current = answer_head;
    char option = 'A';
    while (current != nullptr) {
        cout << option << ": " << current->answer << endl;
        current = current->next;
        option++;
    }
}

// Make question function
Node* make_quiz () {
    // Intialize vairables for later use
    string make_question;
    string q_type = "";
    string question;
    string answer_choice;
    string point_input;
    double points;
    int Q_num = 1;

    Node *question_head = nullptr;
    Node *question_tail = nullptr;

    // Keep running loop while user wants to make questions
    //   - breaks loop when user enters "n"
    do {
        cout << "=== QUESTION " << Q_num << " ===" << endl;

        cout << "Type of question [mcq/tf/wr]: ";
        cin >> q_type;
        cin.ignore();

        while (!check_valid_q_type(q_type)) {
            cout << "[Not a valid question type, please try again!]" << endl;
            cout << "Type of question [mcq/tf/wr]: ";
            cin >> q_type;
            cin.ignore();
            cout << endl;
        }
        cout << endl;
 
        cout << "Enter a question: ";
        getline(cin, question);
        cout << endl;

        // Decision Branch for which question to make
        if (q_type == "mcq") { 
            
            char letter = 'A';
            // Intialize pointers used to reference MCQ choices
            Node* answer_head = nullptr;
            Node* answer_tail = nullptr;

            cout << "[At any time, type 'quit' to exit]" << endl;

            // Iterates over letter, for max of 5 answer choices
            while (letter <= 'E') {
                cout << "Enter Choice " << letter << " :";
                getline(cin, answer_choice);
                if (answer_choice == "quit()") {
                    break;
                }

                // Create answer choice linked list node
                Node* choice_node = new Node(answer_choice, nullptr, nullptr);
                
                // Linking nodes 
                if (answer_head == nullptr) {
                    // if first node in the list
                    answer_head = choice_node;
                    answer_tail = choice_node;
                } else {
                    // add to the end of the list
                    answer_tail->next = choice_node;
                    choice_node->previous = answer_tail;
                    answer_tail = choice_node;
                }
                letter++;
            }
            cout << endl;
            cout << "Select correct answer: ";
            getline(cin, answer_choice);
            answer_choice = to_lowercase(answer_choice);

        // Branch statement for true/false question input
        } else if (q_type == "tf") {
            
            cout << "Select correct answer: ";
            getline(cin, answer_choice);
            answer_choice = to_lowercase(answer_choice);
            
            // Keep asking for valid answer choice until input is correct
            while (answer_choice != "true" && answer_choice != "false") {
                cout << "[Answer not recognized, please try again!]" << endl;

                cout << "Select correct answer: ";
                getline(cin, answer_choice);
                answer_choice = to_lowercase(answer_choice);
            }
        // Branch statement for written question input
        } else if (q_type == "wr") {

            cout << "Enter correct answer: ";
            getline(cin, answer_choice);
        // catch case for incorrect input somehow following through
        } else {
            cout << "Somehow we got here, with an invalid q_type even though we checked it...";
            break;
        }
        // Set non-negative point value for question
        string points_str = "";
       
        while (true) {
            cout << "Enter point value: ";
            getline(cin, points_str);

            try {
            points = std::stod(points_str);
            if (check_point_value(points)) {
                break;
            } else {
                cout << "[Not a valid point value, please try again!]" << endl;
            }
        } catch (const std::invalid_argument& e) {
            cout << "[Invalid input, please enter a numeric value!]" << endl;
        } catch (const std::out_of_range& e) {
            cout << "[Input out of range, please enter a smaller value!]" << endl;
        }
        }
        // Only valid inputs are "y" & "n", everything else reprompts user
        cout << "Question Saved. Continue? [y/n]: ";
        cin >> make_question;
        cin.ignore();
        cout << endl;

        // Create new question node after gathering all info from above prompts
        Node* question_node = new Node(question, answer_choice, Q_num, points, question_node, nullptr);

        // Assign new last based on previous information
        if (question_tail != nullptr) {
            question_tail->next = question_node;
            question_node->previous = question_tail;
        }
        question_tail = question_node;

        // Assign "head" ptr to first question made
        if (question_head == nullptr) {
            question_head = question_node;
        }

        Q_num += 1;
    } while (make_question == "y");

    return question_head;
}

// Checking question
void take_quiz(Node* head) {
    Node* current = head;
    double total_points = 0.0;
    double score = 0.0;
    int num_correct = 0;
    int q_num = 0;
    string input_answer;

    while (current != nullptr) {
        cout << "Question " << current->q_number << ": " << current->question << endl;

        // Microsoft Copilot-assisted syntax for checking if question is MCQ question
        if (current->question.find("mcq") != string::npos) {
            print_mcq_options(current->next); // Assuming the next node is the head of the answer choices
        }

        cout << "Your answer: ";
        getline(cin, input_answer);
        input_answer = to_lowercase(input_answer);

        // Utilizes same Copilot-assisted syntax for checking question type
        if (current->question.find("mcq") != string::npos) {
            while (!check_valid_mcq_answer(current->next, input_answer)) {
                cout << "[Invalid option, please try again!]" << endl;
                cout << "Your answer: ";
                getline(cin, input_answer);
                input_answer = to_lowercase(input_answer);
            }
        }

        if (input_answer == to_lowercase(current->answer)) {
            cout << "Correct!" << endl;
            score += current->points;
            num_correct += 1;
        } else {
            cout << "Incorrect. The correct answer is: " << current->answer << endl;
        }
        total_points += current->points;
        current = current->next;
        q_num++;
        cout << endl;
    }

    cout << "|| Assessment ended. || " << endl;
    cout << "You got " << num_correct << "/" << q_num << " correct." << endl;
    cout << "Your final score is: " << score << " out of " << total_points << endl;
}

// Clear memory function
void clear_memory (Node* question_head) {
    Node* current = question_head;
    while (current != nullptr) {
        Node* next = current->next;
        delete current;
        current = next;
    }
}

int main () {
    Node *question_head = nullptr;
    Node *question_tail = nullptr;

    // Print statement
    cout << "*** Welcome to Nick's Testing Service ***" << endl;
   

    // Call function to create quiz questions 
    question_head = make_quiz();

    // Call function to read through question & take quiz
    take_quiz(question_head);

    // Delete all nodes from memory after usage
    clear_memory(question_head);
}
