#include <cs50.h>
#include <stdio.h>
#include <string.h>

// Max number of candidates
#define MAX 9

// Candidates have name and vote count
typedef struct
{
    string name;
    int votes;
}
candidate;

// Array of candidates
candidate candidates[MAX];

// Number of candidates
int candidate_count;

// Function prototypes
bool vote(string name);
void print_winner(void);

int main(int argc, string argv[])
{
    // Check for invalid usage
    if (argc < 2)
    {
        printf("Usage: plurality [candidate ...]\n");
        return 1;
    }

    candidate_count = argc - 1;
    if (candidate_count > MAX)
    {
        printf("Maximum number of candidates is %i\n", MAX);
        return 2;
    }

    // Populate array of candidates
    for (int i = 0; i < candidate_count; i++)
    {
        candidates[i].name = argv[i + 1];
        candidates[i].votes = 0;
    }

    int voter_count = get_int("Number of voters: ");

    // Loop over all voters
    for (int i = 0; i < voter_count; i++)
    {
        string name = get_string("Vote: ");

        // Check for invalid vote
        if (!vote(name))
        {
            printf("Invalid vote.\n");
        }
    }

    // Display winner of election
    print_winner();
}

// Update vote totals given a new vote
bool vote(string name)
{

    int i;
    int str_idx;
    bool found = false;
    bool isValid = false;

    for(i = 0; i < MAX; i++){

        string candidate_name = candidates[i].name;

        // check if name equals candidate name
        isValid = true;
        str_idx = 0;
        while(name[str_idx] != '\0' && candidate_name[str_idx] != '\0'){
            if(name[str_idx] != candidate_name[str_idx]){
                isValid = false;
                break;
            }
            str_idx++;
        }

        // verify if only substring matched
        if(isValid && (name[str_idx] != '\0' || candidate_name[str_idx] != '\0')){
            isValid = false;
        }
        
        // if valid, increase votes, mark valid candidate found
        if(isValid){
            candidates[i].votes++;
            found = true;
        }
    }

    return found;
}

// Print the winner (or winners) of the election
void print_winner(void)
{

    int i;
    int max_votes = candidates[0].votes;

    // get max_votes
    for(i = 0; i<MAX; i++){
        if(max_votes < candidates[i].votes){
            max_votes = candidates[i].votes;
        }
    }

    // print candidates with max votes
    for(i = 0; i<MAX; i++){
        if(max_votes == candidates[i].votes){
            printf("%s", candidates[i].name);
        }
    }

    return;
}

