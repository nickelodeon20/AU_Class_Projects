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
# include <exception>
using namespace std;


////// Project 4 //////

// Nicholas Schroeder 
// Banner ID: 904328161
// project4_Schroeder_nls0030.cpp

/*  
I prompted Microsoft Copilot for a function that would convert strings
into lowercase copies. I also utilized Copilot for writing with the correct syntax
and an if statement to check for if a question_node is an MCQ question - for 
printing values or checking for valid options. Microsoft Copilot also helped check the 
get_question_by_number() function and catch some errors in intial creation, as well as 
the jump_to_question() function and add_answer()
*/

int Q_num = 1;

class Node {
// to be accessed from anywhere in program
public:
    string question;
    string answer;
    double points;
    int q_number;
    string q_type;
    Node* previous;
    Node* next;
    Node *answer_head;

    // Constructor for Questions
    Node(string q, string a, int q_num, double pts, string type, Node *p, Node *n)
    {
        question = q;
        answer = a;
        q_number = q_num;
        points = pts;
        q_type = type;
        previous = p;
        next = n;
    }
    // Constructor for MCQ answer nodes SPECIFICALLY
    Node(string q, string a, int q_num, double pts, string type, Node *p, Node *n, Node *ah)
    {
        question = q;
        answer = a;
        q_number = q_num;
        points = pts;
        q_type = type;
        previous = p;
        next = n;
        answer_head = ah;
    }
    // Constructor for MCQ Answers
    Node(string a, Node* p, Node* n) {
        answer = a;
        previous = p;
        next = n;
    }
    // User input answer nodes
    Node (int q_num, string a, Node* n) {
        q_number = q_num;
        answer = a;
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

Node* get_question_by_number(Node* head, int q_number) {
    Node* current = head;
    while (current != nullptr) {
        if (current->q_number == q_number) {
            return current;
        }
        current = current->next;
    }
    return nullptr;
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

bool check_valid_question(Node* head, int q_num) {
    Node* current = head;
    while (current != nullptr) {
        if (current->q_number == q_num) {
            return true;
        }
        current = current->next;
    }
    return false;
}
void add_answer(Node*& head, int q_number, const string& input_answer) {
    Node* new_node = new Node{q_number, input_answer, nullptr};
    if (head == nullptr) {
        head = new_node;
    } else {
        Node* current = head;
        while (current->next != nullptr) {
            current = current->next;
        }
        current->next = new_node;
    }
}
void print_answers(Node* head) {
    Node* current = head;
    while (current != nullptr) {
        cout << "Question " << current->q_number << ": " << current->answer << endl;
        current = current->next;
    }
}
void jump_to_question(Node* current) {
    Node *head = nullptr;
    // Jump between questions functionality
        cout << "Enter question number to jump to or press Enter to continue: ";
        string jump_input;
        getline(cin, jump_input);
        if (!jump_input.empty()) {
            int jump_to = stoi(jump_input);
            Node* jump_node = get_question_by_number(head, jump_to);
            if (jump_node != nullptr) {
                current = jump_node;
            } else {
                cout << "Invalid question number. Continuing to next question." << endl;
                current = current->next;
            }
        } else {
            current = current->next;
        }
    }
void get_mcq_answers () {
    string answer_choice;
    Node* answer_head = nullptr;
    Node* answer_tail = nullptr;    

    char letter = 'A';
        // Intialize pointers used to reference MCQ choices
        

        cout << "[At any time, type 'quit()' to exit]" << endl;

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
}
// Make question function
Node* make_question (Node*& question_tail) {
    // Intialize vairables for later use
    string make_question;
    string q_type = "";
    string question;
    string answer_choice;
    string point_input;
    double points;

    Node* question_head = nullptr;

    // For MCQ specifically
    Node* answer_head = nullptr;
    Node* answer_tail = nullptr;

    // Keep running loop while user wants to make questions
    //   - breaks loop when user enters "n"
    
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

        get_mcq_answers();

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
    }
    // Set non-negative point value for question
    cout << "Enter point value: ";
    cin >> points;
    cin.ignore();
    cout << endl;

    while (!check_point_value(points)) {
        cout << "[Not a valid point value, please try again!]" << endl;
        cout << "Enter point value: ";
        cin >> points;
        cin.ignore();
        cout << endl;
    }
    cout << "Question Saved." << endl;

    Node *question_node = nullptr;

    // Create new question node after gathering all info from above prompts
    if (q_type == "mcq") {
        Node* question_node = new Node(question, answer_choice, Q_num, points, q_type, question_node, nullptr, answer_head);
    } else {
        Node* question_node = new Node(question, answer_choice, Q_num, points, q_type, question_node, nullptr);
    }

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

    return question_head;
}

// In function form to be utilized in a loop in edit_question()
void num_chosen_to_edit(Node* question_head, int num_chosen) {
    // Change the Question type (for some reason? Idk why you would do this)
    if (num_chosen == 1) {
        string new_q_type = "";
        cout << "Choose a new question type [mcq/tf/wr]: ";
        getline(cin, new_q_type);
        if (!check_valid_q_type) {
            cout << "Invalid question type, cannot edit question" << endl;
            return;
        } else if (new_q_type != question_head->q_type) {
            make_question(question_head->next);
        } else {
            cout << "Why are you changint the type instead of making a new question??????";
        }
    // Change the Question 
    } else if (num_chosen ==2 ) {
        string new_question = "";
        cout << "Choose a new question: ";
        getline(cin, new_question);
        question_head->question = new_question;

    // Change the answer choices
    } else if (num_chosen == 3) {
        if (question_head->q_type == "mcq") {
            get_mcq_answers();
        } else if (question_head->q_type == "tf" || question_head->q_type == "wr") {
            string new_answer = "";
            cout << "Choose a new answer: ";
            getline(cin, new_answer);
            question_head->answer = new_answer;
        } else {
            cout << "ERROR!!! ERROR!!! ERROR!!!" << endl;
        }
    } else if (num_chosen == 4) {
        if (question_head->q_type == "mcq") {
            string new_correct_answer = "";
            cout << "Choose a new correct answer: ";
            getline(cin, new_correct_answer);
            check_valid_mcq_answer(question_head->answer_head, new_correct_answer);
            question_head->answer = new_correct_answer;
        } else {
            string new_correct_answer = "";
            cout << "Choose a new correct answer: ";
            getline(cin, new_correct_answer);
            question_head->answer = new_correct_answer;
        }
    } else {
        cout << "[Invalid number chosen, please choose again!]" << endl;
    }
}



// Edit question function
void edit_question(Node* question_head) {
    if (question_head == nullptr) {
        cout << "No questions available to edit." << endl;
        return;
    }
    int q_num;
    cout << "Enter the question number you want to edit: ";
    cin >> q_num;
    cin.ignore();

    Node* current = question_head;
    if (!check_valid_question(current, q_num)) {
        cout << "[Question number " << q_num << " does not exist, please try again!]" << endl;
        return;
    }
    string new_question;
    string new_answer;
    double new_points;

    cout << "=========================================" << endl;
    cout << "===  QUESTION" << q_num << " VALUES    " << endl;
    cout << "=========================================" << endl;

    cout << "1. Type: " << question_head->q_type << endl;
    cout << "2. Question: " << question_head->question << endl;
    cout << "3. Answer Choices: " << endl;

    // Printing specifically through MCQ linked list of options, & all other options
    if (question_head->q_type == "mcq") {
        Node* ptr = question_head->answer_head;
        print_mcq_options(ptr);
    } else if (question_head->q_type == "tf") {
        cout << question_head->answer << endl;
    } else if (question_head->q_type == "wr") {
        cout << question_head->answer << endl;
    } else {
        cout << "Somehow we have an invalid q_type error ***audible sigh***" << endl;
    }
    cout << "4. Correct Answer: " << question_head->answer << endl;
    cout << "=========================================" << endl;

    string num_to_edit = " ";
    cout << "Type a number to edit, or type quit(): ";
    getline(cin, num_to_edit);

    if (num_to_edit == "quit()") {
        // do nothing here, just skip over everthing
    } else {
        // Convert string line to number
        try {
            int num_chosen = std::stoi(num_to_edit);
            num_chosen_to_edit(question_head, num_chosen);
        } catch (const std::invalid_argument& e) {
            std::cerr << "Invalid argument: " << e.what() << std::endl;
        }
    }

    cout << "Enter new point value (or press Enter to keep current): ";
    string point_input;
    getline(cin, point_input);
    if (!point_input.empty()) {
        new_points = stod(point_input);
        if (check_point_value(new_points)) {
            current->points = new_points;
        } else {
            cout << "[Invalid point value, keeping current value.]" << endl;
        }
    }

    cout << "Question " << q_num << " updated successfully." << endl;
}


// Delete question action
void delete_question(Node*& question_head, int q_num) {
    if (question_head == nullptr) {
        cout << "No questions available to delete." << endl;
        return;
    }

    Node* current = question_head;
    Node* previous = nullptr;

    // Go through the list to find the node to delete
    current = get_question_by_number(current, q_num);

    // If the node was not found
    if (current == nullptr) {
        cout << "Question number " << q_num << " not found." << endl;
        return;
    }

    // If the node to delete is the head of the list
    if (current == question_head) {
        question_head = question_head->next;
        if (question_head != nullptr) {
            question_head->previous = nullptr;
        }
    } else {
        previous->next = current->next;
        if (current->next != nullptr) {
            current->next->previous = previous;
        }
    }

    delete current;
    cout << "Question number " << q_num << " deleted successfully." << endl;
}



// Checking question list and 
void take_quiz(Node* head) {
    Node* current = head;
    Node *ptr = nullptr;
    double total_points = 0.0;
    double score = 0.0;
    int num_correct = 0;
    int q_num = 1;
    string input_answer;

    string yn = "";

    cout << "|| Begin Assessment? [y/n]" << endl;
    getline(cin, yn);
    if (yn == "n") {
        return;
    } else if (yn == "y") {
        cout << "Starting assessment..." << endl;
    } else {
        cout << "Invalid response." << endl;
        return;
    }

    
    while (current != nullptr) {
        cout << "Question " << current->q_number << ": " << current->question << endl;

        // Checks specifically for MCQ questions
        if (current->q_type == "mcq") {
            print_mcq_options(current->next); // Assuming the next node is the head of the answer choices
        }

        cout << "Your answer: ";
        getline(cin, input_answer);
        input_answer = to_lowercase(input_answer);
        
        // Add answer to list for later printing
        add_answer(ptr, q_num, input_answer);

        // Checks q_type variable specifically for MCQ questions
        if (current->q_type == "mcq") {
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
        // Complete incrementations of all variables
        total_points += current->points;
        current = current->next;
        q_num++;
        cout << endl;

        jump_to_question(current);
    }
    cout << "|| Assessment ended. || " << endl << endl << endl;

    cout << "====================" << endl << "=== SESSION LOG  ===" << endl << "====================" << endl;
    cout << "Correct Answers: " << num_correct << "/" << q_num << endl;

    print_answers(ptr);

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
// to be called to choose operation
int choose_operation () {
    int operation;
    
    cout << "What operation do you want to do?" << endl;
    cout << "\t1. Make question (1)\n\t2. Edit question (2)\n\t3. Delete question (3)\n\t4. Finish (4)" << endl;
    cout << "Select an operation: ";
    cin >> operation;
    cout << endl;

    return operation;
}



// Unit testing, only functioning during DEBUG mode
void run_unit_tests() {
    // Unit Test Case 1: Ask no question
    // Unit Test Case 1: Ask no question
    cout << "Unit Test Case 1: Ask no question. The program should give a warning message." << endl;
    Node* empty_quiz = nullptr;
    take_quiz(empty_quiz);
    cout << "Case 1 Passed" << endl;

    // Unit Test Case 2: Check valid true/false answers
    cout << "Unit Test Case 2: Check valid true/false answers." << endl;
    assert(check_valid_tf("true") == true);
    assert(check_valid_tf("false") == true);
    assert(check_valid_tf("yes") == false);
    cout << "Case 2 Passed" << endl;

    // Unit Test Case 3: Check valid question number
    cout << "Unit Test Case 3: Check valid question number." << endl;
    Node* quiz = new Node{"What is 2 + 2?", "4", 1, 1.0, "wr", nullptr, nullptr};
    Node* quiz_plus_one = new Node{"What is the capital of France?", "paris", 2, 1.0, "wr", quiz, nullptr};
    quiz->next = quiz_plus_one;
    assert(check_valid_question(quiz, 1) == true);
    assert(check_valid_question(quiz, 2) == true);
    assert(check_valid_question(quiz, 3) == false);
    cout << "Case 3 Passed" << endl;

    // Unit Test Case 4: Add answer to linked list
    cout << "Unit Test Case 4: Add answer to linked list." << endl;
    Node* answers = nullptr;
    add_answer(answers, 1, "4");
    add_answer(answers, 2, "paris");
    assert(answers->q_number == 1);
    assert(answers->answer == "4");
    assert(answers->next->q_number == 2);
    assert(answers->next->answer == "paris");
    cout << "Case 4 Passed" << endl;

    // Unit Test Case 5: Print answers
    cout << "Unit Test Case 5: Print answers." << endl;
    cout << "Expected output:" << endl;
    cout << "Question 1: 4" << endl;
    cout << "Question 2: paris" << endl;
    cout << "Actual output:" << endl;
    print_answers(answers);
    cout << "Case 5 Passed" << endl;
    
    // Clean up memory
    while (quiz != nullptr) {
        Node* temp = quiz;
        quiz = quiz->next;
        delete temp;
    }
    while (answers != nullptr) {
        Node* temp = answers;
        answers = answers->next;
        delete temp;
    }

}


# define TEST
int main () {
    Node *question_head = nullptr;
    Node *question_tail = nullptr;
    int operation;
    bool valid = false;

#ifdef TEST
    cout << "*** This is a debugging version ***" << endl;
    run_unit_tests();

#else
    // Print statement
    cout << "*** Welcome to Nick's Testing Service ***" << endl;
    operation = choose_operation();

    while (!valid && operation != 4) {
        // Make new question operation
        if (operation == 1) {
            question_head = make_question(question_tail);

            valid = true;

        // Edit existing question operation
        } else if (operation ==2 ) {
            edit_question(question_head);

            valid = true;
        
        // Delete question operation
        } else if (operation == 3) {
            delete_question(question_head, question_head->q_number);

            valid = true;

        // Anything else, not inlcuding 4 (b/c loop won't run)
        //   - 4 -> Finish, dont continue operations
        } else {
            cout << "[Invalid operation chosen, please choose again!]" << endl;
            choose_operation();
        }
    }
    Node* current = question_head;
    int pts_total = 0;
    int questions_total = 0;

    // Iterator through to get total points, questions before assessment
    while (current != nullptr) {
        questions_total += 1;
        pts_total += current->points;
        Node* next = current->next;
        current = next;
    }
    cout << "Total questions: " << questions_total << endl;
    cout << "Total points: " << pts_total << endl;
    

    // Call function to read through question & take quiz
    take_quiz(question_head);

    cout << "*** Thank you for using the testing service. Goodbye! ***" << endl;

    // Delete all nodes from memory after usage
    clear_memory(question_head);
#endif
}

