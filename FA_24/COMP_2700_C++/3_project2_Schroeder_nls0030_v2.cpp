# include <iostream>
using namespace std;
# include <stdlib.h>
# include <assert.h>
# include <ctime>
# include <iomanip>

/* Programming Environment:
Write a program in C++. Compile and run it using AU server (no matter what kind of text
editor, IDE or coding environment you use, please make sure your code could run on AU
server, the only test bed we accept is the AU server). */


////// Project 2 //////

// Nicholas Schroeder 
// Banner ID: 904328161
// project2_Schroeder_nls0030_v1.cpp

// To compile this code, go to the AU server file under the nls0030 file and run the
// command "g++ project2_Schroeder_nls0030_v1.cpp -o out && -./out", which will compile and
// run the file (for project 2)

// I used Google searches for syntax assisstance, such as the setprecision() method from 
// iomanip and decimal division in C++ (which is printed using setprecision()). I also discuessed
// with my classmate Lucius Renshaw the logic behind the testdriver functions, namely 
// test_at_least_two_alive() and test_Aaron_shoots2, although the actual implementation of that 
// code is my own. And I learned from GTA Hai Hoang Vu how to upload the two separate files to
// the AU server and use the git repository to combine the files, basic error-checking my code 
// (i.e. realizing I was not calling the testdriver functions), and vim editor commands during 
// office hours.


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
void print_total_truel_results(int A_wins, int B_wins, int C_wins) {
    double runs_double = (double)runs;
    double A_wins_double = (double)A_wins;
    double B_wins_double = (double)B_wins;
    double C_wins_double = (double)C_wins;

    double A_wins_percent = 100*(A_wins_double / runs_double);
    double B_wins_percent = 100*(B_wins_double / runs_double);
    double C_wins_percent = 100*(C_wins_double / runs_double);

    cout << "Aaron won " << A_wins << "/" << runs << " truels or " << setprecision(4) << A_wins_percent << "%\n";
    cout << "Bob won " << B_wins << "/" << runs << " truels or " << setprecision(4) << B_wins_percent << "%\n";
    cout << "Charlie won " << C_wins << "/" << runs << " truels or " << setprecision(4) << C_wins_percent << "%\n";
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
    if (A_alive && B_alive) {
        B_alive = false;
    } else if (A_alive && !B_alive) {
        A_alive = false;
    } else if (!A_alive && B_alive) {
        B_alive = false;
    } else {
        cout << "Charlie has already won the truel, why is this running?";
    }
}
/* Call by reference
* Input: A_alive indicates if Aaron is alive or dead
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

/* Implementation of the test driver for at_least_two_alive() */
void test_at_least_two_alive(void) {
	cout << "Unit Testing 1: Function – at_least_two_alive()\n";
	// Case 1
	cout << "\tCase 1: Aaron alive, Bob alive, Charlie alive\n";
	assert(true == at_least_two_alive(true, true, true));
	cout << "\tCase passed ...\n";
	// Case 2
	cout << "\tCase 2: Aaron dead, Bob alive, Charlie alive\n";
	assert(true == at_least_two_alive(false, true, true));
	cout << "\tCase passed ...\n";
	// Case 3
	cout << "\tCase 3: Aaron alive, Bob dead, Charlie alive\n";
	assert(true == at_least_two_alive(true, false, true));
	cout << "\tCase passed ...\n";
	// Case 4
	cout << "\tCase 4: Aaron alive, Bob alive, Charlie dead\n";
	assert(true == at_least_two_alive(true, true, false));
	cout << "\tCase passed ...\n";
	// Case 5
	cout << "\tCase 5: Aaron dead, Bob dead, Charlie alive\n";
	assert(false == at_least_two_alive(false, false, true));
	cout << "\tCase passed ...\n";
	// Case 6
	cout << "\tCase 6: Aaron dead, Bob alive, Charlie dead\n";
	assert(false == at_least_two_alive(false, true, false));
	cout << "\tCase passed ...\n";
	// Case 7
	cout << "\tCase 7: Aaron alive, Bob dead, Charlie dead\n";
	assert(false == at_least_two_alive(true, false, false));
	cout << "\tCase passed ...\n";
	// Case 8
	cout << "\tCase 8: Aaron dead, Bob dead, Charlie dead\n";
	assert(false == at_least_two_alive(false, false, false));
	cout << "\tCase passed ...\n";
    cout << "\n";
}

void test_Aaron_shoots1() {
	cout << "Unit Testing 2: Function Aaron_shoots1(Bob_alive, Charlie_alive)\n";

	// Case 1
	bool bob_a = true, charlie_a = true;
	cout << "\tCase 1: Bob alive, Charlie alive\n"
		 << "\t\tAaron is shooting at Charlie\n";
	Aaron_shoots1(bob_a, charlie_a);
	assert(true == bob_a);
        cout << "\tCase passed ...\n";


	// Case 2
	bob_a = false, charlie_a = true;
	cout << "\tCase 2: Bob dead, Charlie alive\n"
		 << "\t\tAaron is shooting at Charlie\n";
	Aaron_shoots1(bob_a, charlie_a);

	// Case 3
	bob_a = true, charlie_a = false;
	cout << "\tCase 3: Bob alive, Charlie dead\n"
		 << "\t\tAaron is shooting at Bob\n";
	Aaron_shoots1(bob_a, charlie_a);
    cout << "\n";
}

void test_Bob_shoots() {
	cout << "Unit Testing 3: Function Bob_shoots(Aaron_alive, Charlie_alive)\n";

	// Case 1
	bool aaron_a = true, charlie_a = true;
	cout << "\tCase 1: Aaron alive, Charlie alive\n"
		 << "\t\tBob is shooting at Charlie\n";
	Bob_shoots(aaron_a, charlie_a);

	// Case 2
	aaron_a = false, charlie_a = true;
	cout << "\tCase 1: Aaron dead, Charlie alive\n"
		 << "\t\tBob is shooting at Charlie\n";
	Bob_shoots(aaron_a, charlie_a);

	// Case 3
	aaron_a = true, charlie_a = false;
	cout << "\tCase 3: Aaron alive, Charlie dead\n"
		 << "\t\tBob is shooting at Aaron\n";
	Bob_shoots(aaron_a, charlie_a);
    cout << "\n";
}

void test_Charlie_shoots(){
	cout << "Unit Testing 4: Function Charlie_shoots(Aaron_alive, Bob_alive)\n";

	// Case 1
	bool aaron_a = true, bob_a = true;
	cout << "\tCase 1: Aaron alive, Bob alive\n"
		 << "\t\tCharlie is shooting at Bob\n";
	Charlie_shoots(aaron_a, bob_a);
	assert(false == bob_a && true == aaron_a);

	// Case 2
	aaron_a = false, bob_a = true;
	cout << "\tCase 1: Aaron dead, Bob alive\n"
		 << "\t\tCharlie is shooting at Bob\n";
	Charlie_shoots(aaron_a, bob_a);
	assert(false == bob_a && false == aaron_a);

	// Case 3
	aaron_a = true, bob_a = false;
	cout << "\tCase 3: Aaron alive, Bob dead\n"
		 << "\t\tCharlie is shooting at Aaron\n";
	Charlie_shoots(aaron_a, bob_a);
	assert(false == aaron_a && false == bob_a);
    cout << "\n";
}

void test_Aaron_shoots2(){
	cout << "Unit Testing 5: Function Aaron_shoots2(Bob_alive, Charlie_alive)\n";

	// Case 1
	bool bob_a = true, charlie_a = true;
	cout << "\tCase 1: Bob alive, Charlie alive\n"
		 << "\t\tAaron intentionally misses his first shot\n"
		 << "\t\tBoth Bob and Charlie are alive.\n";
	Aaron_shoots2(bob_a, charlie_a);
	assert(bob_a == true && charlie_a == true);

	// Case 2
	bob_a = false, charlie_a = true;
	cout << "\tCase 2: Bob dead, Charlie alive\n"
		 << "\t\tAaron is shooting at Charlie\n";
	Aaron_shoots2(bob_a, charlie_a);

	// Case 3
	bob_a = true, charlie_a = false;
	cout << "\tCase 3: Bob alive, Charlie dead\n"
		 << "\t\tAaron is shooting at Bob\n";
	Aaron_shoots2(bob_a, charlie_a);
    cout << "\n";
}

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
    test_at_least_two_alive();
    test_Aaron_shoots1();
    test_Bob_shoots();
    test_Charlie_shoots();
    test_Aaron_shoots2();
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
    print_total_truel_results(A_wins, B_wins, C_wins);
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
    print_total_truel_results(A_wins, B_wins, C_wins);
    int S2_Aaron = A_wins;

    if (S1_Aaron < S2_Aaron) {
        // Correct
        cout << "Strategy 2 is better than Strategy 1.\n";
    } else if (S2_Aaron < S1_Aaron) {
        // Incorrect
        cout << "Strategy 1 is better than Strategy 2.\n";
    } else {
        cout << "Error, they are equal somehow lol\n";
    }
}

