// Nicholas Schroeder
// nls0030
// project1_Schroeder_nls0030.cpp

// Compile code by going to file terminal, then running
// g++ project1_Schroeder_nls0030.cpp to produce an 
// executable. Once that is done, type "./{executable name}"
// to run the executable. After providing the input numbers, 
// 


// Write program for how many months to pay loan, total interest,
// monthly interest paid, total debt left

// Counter - # of months & loop iterations --> DONE
//        *** when loan = 0

// Interest calculator for total debt left (per year -> #/12)
//   - Var to sum for total interest
//   - Output monthly interest in table format

// Check for payment >> interest amount --> ask for higher payment
// Interest rate = non-negative / Loan, payment = positive

#include <iostream>
using namespace std;

int main() {
    // Intialize variables used in program

    // Variables from user input
    double input_loan;
    double input_interest_rate;
    double input_payments;

    int low_bal_interest;
    int final_total_interest = 0;
    int final_principal_payment = 0;

    // Sum total variables
    double principal_payment;
    int months = 0;
    double total_interest = 0;
    double balance = 0;

    // Print prompts and receive input from user
    cout << "Loan Amount: ";
    cin >> input_loan;
    cout << "\n";
    cout << "Interest Rate (% per year): ";
    cin >> input_interest_rate;
    cout << "\n";
    cout << "Monthly Payments: ";
    cin >> input_payments;
    cout << "\n";

    balance = input_loan;

    // Calculate monthly interest from yearly interest
    double month_interest_rate = input_interest_rate / 12.0 / 100.0;

    cout << "*****************************************************************\n";
    cout << left << "      Amortization table\n";
    cout << "*****************************************************************\n";
    cout.width(14);
    cout << "Months";
    cout.width(14);
    cout << "Balance";
    cout.width(14);
    cout << "Payments";
    cout.width(14);
    cout << "Rate";
    cout.width(14);
    cout << "Interest";
    cout.width(14);
    cout << "Principal\n";

    ///// first payment print results //////
    // print input_loan
    // print

        while (balance > 0)
    {
        ////// Calculations //////
        int m_interest;
        if (balance < 50) { 
                double m_interest = balance * month_interest_rate;
                total_interest += m_interest;

                principal_payment = balance - m_interest;

                balance = 0;
        } else {
            double m_interest = balance * month_interest_rate;
                total_interest += m_interest;

                principal_payment = input_payments - m_interest;

                balance = balance + m_interest - input_payments;
        }
        // count months
        months += 1;
        cout.width(14);
        cout << months;

        // calculate/print total loan (balance)
        cout.setf(ios::fixed);
        cout.setf(ios::showpoint);
        cout.precision(2);
        cout << "$";
        cout.width(14);
        cout << left << balance;

        // print payment
        cout << "$";
        cout.width(14);
        cout << input_payments;

        // print interest rate

        cout.width(14);
        cout << input_interest_rate / 12;

        // print interest (based off previous month total loan)
        cout << "$";
        cout.width(14);
        cout << m_interest;

        // print principal loan payment
        cout << "$";
        cout.width(14);
        cout << principal_payment;
        cout << endl;
    }
    cout << "**************************************************\n";
    // print total months it takes to pay loan
    cout << left << "It takes " << months << " months to pay off the loan.\n";
    
    // print total interest on loan
    cout << left << "Total interest paid is : $" << total_interest << "\n";

    return 0;
}
