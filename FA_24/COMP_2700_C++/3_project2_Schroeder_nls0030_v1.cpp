# include <iostream>
using namespace std;
# include <stdlib.h>
# include <assert.h>
# include <ctime>

/* Programming Environment:
Write a program in C++. Compile and run it using AU server (no matter what kind of text
editor, IDE or coding environment you use, please make sure your code could run on AU
server, the only test bed we accept is the AU server). */

// Variables
const int runs = 10000;
const bool C_hit = true;
const bool A_hit_2 = false;

// prototypes
bool at_least_two_alive(bool A_alive, bool B_alive, bool C_alive);
/* Input: A_alive indicates whether Aaron is alive */
/* B_alive indicates whether Bob is alive */
/* C_alive indicates whether Charlie is alive */

/* Return: true if at least two are alive */
/* otherwise return false */
/*
* Add other function prototypes below
*/
void Aaron_shoots1(bool &B_alive, bool &C_alive);

void Bob_shoots(bool &A_alive, bool &C_alive);

void Charlie_shoots(bool &A_alive, bool &B_alive);

void Aaron_shoots2(bool &B_alive, bool &C_alive);

void test_at_least_two_alive(void);
/* This is a test driver for at_least_two_alive() */
/*
* Add more prototypes for other test drivers below
*/
void test_Aaron_shoots1(void);

void test_Bob_shoots(void);

void test_Charlie_shoots(void);

void test_Aaron_shoots2(void);

/////////////
// Extra functions to use 
/////////////

void press_key_to_continue () {
    // Pause Command for Linux Terminal
    cout << "Press any key to continue...\n";
    cin.ignore().get();
}

// Print function for results
bool print_total_truel_results(int A_wins, int B_wins, int C_wins) {
    float A_wins_percent = A_wins / runs;
    float B_wins_percent = B_wins / runs;
    float C_wins_percent = C_wins / runs;

    cout << "Aaron won " << A_wins << "/" << runs << " truels or " << A_wins_percent << "%\n";
    cout << "Bob won " << B_wins << "/" << runs << " truels or " << B_wins_percent << "%\n";
    cout << "Charlie won " << C_wins << "/" << runs << " truels or " << C_wins_percent << "%\n";
    return true;
}

bool shot_result (char Person) {
    // Intialize rand seed, and generate random number
    int chance = rand() % 100;
    //cout << chance << " - number made\n";

    // Strategy 1 - Aaron fires 
    if (Person == 'A') {
        if (chance < 33) {
            return true;
        } else {
            return false;
        }
    // Strategy 1 & 2 - Bob fires
    } else if (Person == 'B') { 
        if (chance < 50) {
            return true;
        } else {
            return false;
        }
    } else {
        // Code error/type - should only be used by S1 Aaron, S1-2 Bob
        return false;
    }
}

// Returns char identity of survivor
char Strategy_1 (int turn, bool &A_alive, bool &B_alive, bool &C_alive, char Person) {
    // Loop for single truel
    char survivor;
    while (at_least_two_alive(A_alive, B_alive, C_alive)) {
        int turn_check = turn % 3;
        // Check for whose turn to shoot
        if (turn == 0 && A_alive) {
            Person = 'A';
        } else if (turn == 1 && B_alive) {
            Person = 'B';
        } else if (turn == 2 && C_alive) {
            Person = 'C';
        } else if (turn > 2) {
            if (A_alive && turn_check == 0) {
                Person = 'A';
            } else if (B_alive && turn_check == 1) {
                Person = 'B';
            } else if (C_alive && turn_check == 2) {
                Person = 'C';
        }
        }

        // Run shoot function
        if (Person == 'A') {
            Aaron_shoots1(B_alive, C_alive);
            //cout << A_alive << " - A_alive | ";
            //cout << B_alive << " - B_alive | ";
            //cout << C_alive << " - C_alive\n";
        } else if (Person == 'B') {
            Bob_shoots(A_alive, C_alive);
            //cout << A_alive << " - A_alive | ";
            //cout << B_alive << " - B_alive | ";
            //cout << C_alive << " - C_alive\n";
        } else if (Person == 'C') {
            Charlie_shoots(A_alive, B_alive);
            //cout << A_alive << " - A_alive | ";
            //cout << B_alive << " - B_alive | ";
            //cout << C_alive << " - C_alive\n";
        } else {
            cout << "Typo/Error, invalid person";
            break;
        }
        turn += 1;
    }
    if (A_alive) {
        survivor = 'A';
    } else if (B_alive) {
        survivor = 'B';
    } else if (C_alive) {
        survivor = 'C';
    } else {
        cout << "Typo/Error, invalid person";
    }
    A_alive = true;
    B_alive = true;
    C_alive = true;
    return survivor;
}

char Strategy_2 (int turn, bool &A_alive, bool &B_alive, bool &C_alive, char Person) {
    // Aaron misses his first shot, so effectively his turn is skipped
    turn = 1;
    char survivor = Strategy_1(turn, A_alive, B_alive, C_alive, Person);
    return survivor;
}

/* Implementation of at_least_two_alive() */
bool at_least_two_alive(bool A_alive, bool B_alive, bool C_alive) {
    // If two of A, B, or C are true (>=2 alive) --> return true (continue truel)
    if ((A_alive && B_alive) || (A_alive && C_alive) || (B_alive && C_alive)) {
        return true;
    // If <2 of A, B or C are true (<2 alive) --> return false (ending run)
    } else {
        return false;
    }
}

void Aaron_shoots1(bool& B_alive, bool& C_alive) {
    // T/F if Aaron hit somebody
    bool hit = shot_result('A');

    // What happens after Aaron shoots
    if (B_alive && C_alive && hit) {
        C_alive = false;
    } else if (B_alive && hit) {
        B_alive = false;
    } else if (C_alive && hit) {
        C_alive = false;
    } else {
        // No one was hit
        B_alive = B_alive;
        C_alive = C_alive;
    }
}
/* Strategy 1: Use call by reference
* Input: B_alive indicates whether Bob alive or dead
* C_alive indicates whether Charlie is alive or dead
* Return: Change B_alive into false if Bob is killed.
* Change C_alive into false if Charlie is killed.
*/
void Bob_shoots(bool& A_alive, bool& C_alive) {
    // T/F if Bob hit somebody
    bool hit = shot_result('B');

    // What happens after Bob shoots
    if (A_alive && C_alive && hit) {
        C_alive = false;
    } else if (A_alive && hit) {
        A_alive = false;
    } else if (C_alive && hit) {
        C_alive = false;
    } else {
        // No one was hit, so everything stays
        A_alive = A_alive;
        C_alive = C_alive;
    }
}
/* Call by reference
* Input: A_alive indicates if Aaron is alive or dead
* C_alive indicates whether Charlie is alive or dead
* Return: Change A_alive into false if Aaron is killed.
* Change C_alive into false if Charlie is killed.
*/
void Charlie_shoots(bool& A_alive, bool& B_alive) {
    // T/F if Charlie hit somebody (SPOILER: he did)
    bool hit = C_hit;
}
/* Call by reference
* Input: A_alive indicates if Aaron is alive or dead
5
* B_alive indicates whether Bob is alive or dead
* Return: Change A_alive into false if Aaron is killed.
* Change B_alive into false if Bob is killed.
*/
void Aaron_shoots2(bool& B_alive, bool& C_alive) {
    // Aaron intentionally misses
    bool hit = A_hit_2;
}
/* Strategy 2: Use call by reference
* Input: B_alive indicates whether Bob alive or dead
* C_alive indicates whether Charlie is alive or dead
* Return: Change B_alive into false if Bob is killed.
* Change C_alive into false if Charlie is killed.
*/



// Main function body 
int main() {
    bool A_alive = true;
    bool B_alive = true;
    bool C_alive = true;
    int A_wins = 0;
    int B_wins = 0;
    int C_wins = 0;
    
    int count = 0;
    int turn = 0;
    char Person = ' ';
    srand(time(0));

    cout << "*** Welcome to Nick's Truel of the Fates Simulator ***\n";

    //               //
    //   Strategy 1  //
    //               //
    cout << "Ready to test Strategy 1 (run " << runs << " times): \n";
    press_key_to_continue();

    // loop for # of truels
    while (count < runs) {
        Person = Strategy_1(turn, A_alive, B_alive, C_alive, Person);
        //cout << Person << " is alive\n";
        if (Person == 'A') {
            A_wins += 1;
        } else if (Person == 'B') {
            B_wins += 1;
        } else if (Person == 'C') {
            C_wins += 1;
        } else {
            cout << "Typo/Error, invalid person";
            break;
        } 
        //cout << turn << " order\n";
        count += 1;
    }
    bool printed_s1 = print_total_truel_results(A_wins, B_wins, C_wins);
    int S1_Aaron = A_wins;
    A_wins = 0;
    B_wins = 0;
    C_wins = 0;
    count = 0;
    turn = 0;

    //               //
    //   Strategy 2  //
    //               //

    // Aaron intentionally misses his first shot so the other two shooters, 
    // who both have a much higher chance of hitting their targets (which 
    // are each other for their first shots), so he increases his own odds
    // of getting a second shot and possibly winning the round.
    cout << "Ready to test Strategy 2 (run " << runs << " times): \n";
    press_key_to_continue();
    while (count < runs) {
        Person = Strategy_2(turn, A_alive, B_alive, C_alive, Person);
        if (Person == 'A') {
            A_wins += 1;
        } else if (Person == 'B') {
            B_wins += 1;
        } else if (Person == 'C') {
            C_wins += 1;
        } else {
            cout << "Typo/Error, invalid person";
            break;
        }
        turn += 1;
        count += 1;
    }
    bool printed_s2 = print_total_truel_results(A_wins, B_wins, C_wins);
    int S2_Aaron = A_wins;

    if (S1_Aaron < S2_Aaron) {
        // Correct
        cout << "Strategy 2 is better than Strategy 1.";
    } else if (S2_Aaron < S1_Aaron) {
        // Incorrect
        cout << "Strategy 1 is better than Strategy 2.";
    } else {
        cout << "Error, they are equal somehow lol";
    }
}
