#include <fstream>
#include <iostream>
#include <stack>

#define MAP_SIZE 7

#define FIRST_PLAYER 1
#define SECOND_PLAYER 2
#define BLOCKED -1
#define ALLOWED 0

#define MAP_DIR "map.dat"
#define MOVE_DIR "move.dat"
#define BLUE_DIR "blue.dat"
#define RED_DIR "red.dat"

using namespace std;

int map[MAP_SIZE][MAP_SIZE];
int turn;

/**
 * This method calculates available moves for next step from initial state
 * but ignores block cells
*/
void get_paths(int row, int column, bool graph[MAP_SIZE * MAP_SIZE][MAP_SIZE * MAP_SIZE])
{
    int size = MAP_SIZE * MAP_SIZE;

    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            int node_index = i * MAP_SIZE + j;

            int backRow = -1;
            int backDiagnol = -1;
            int forwardDiagnol = -1;
            int upColumn = -1;

            if (i - 1 >= 0 && j - 1 >= 0)
            {
                backDiagnol = (i - 1) * MAP_SIZE + (j - 1);
            }
            if (i - 1 >= 0)
            {
                upColumn = (i - 1) * MAP_SIZE + j;
            }
            if (j - 1 >= 0)
            {
                forwardDiagnol = (i + 1) * MAP_SIZE + (j - 1);
                backRow = i * MAP_SIZE + (j - 1);
            }

            if (row == i && backRow < size && backRow >= 0)
            {
                graph[backRow][node_index] = true;
                graph[node_index][backRow] = true;
            }

            if (column == j && upColumn < size && upColumn >= 0)
            {
                graph[upColumn][node_index] = true;
                graph[node_index][upColumn] = true;
            }

            if (row - i == column - j && backDiagnol < size && backDiagnol >= 0)
            {
                graph[backDiagnol][node_index] = true;
                graph[node_index][backDiagnol] = true;
            }

            if (row - i == j - column && forwardDiagnol < size && forwardDiagnol >= 0)
            {
                graph[forwardDiagnol][node_index] = true;
                graph[node_index][forwardDiagnol] = true;
            }
        }
    }
}

/**
 * This method calculates available moves for next step from initial state
 * also calculates block cells.
*/
void DFS(bool paths[MAP_SIZE * MAP_SIZE][MAP_SIZE * MAP_SIZE], int ans[MAP_SIZE][MAP_SIZE], int map[MAP_SIZE][MAP_SIZE], int node)
{
    int nodes = MAP_SIZE * MAP_SIZE;
    bool check[MAP_SIZE];

    stack<int> stack;

    stack.push(node);

    while (!stack.empty())
    {
        int vertex = stack.top();

        bool possibility = false;
        for (int i = 0; i < nodes; i++)
        {
            if (paths[vertex][i] && !check[i] && map[i % MAP_SIZE][i / MAP_SIZE] == ALLOWED)
            {
                stack.push(i);
                possibility = true;
                check[i] = true;

                ans[i % MAP_SIZE][i / MAP_SIZE] = ALLOWED;
                break;
            }
        }

        if (!possibility)
        {
            stack.pop();
        }
    }
}

void read_map()
{
    fstream map_file(MAP_DIR);

    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            char c;
            map_file >> c;

            if (c == '1')
                map[i][j] = ALLOWED;
            if (c == '0')
                map[i][j] = BLOCKED;
            if (c == 'f')
                map[i][j] = FIRST_PLAYER;
            if (c == 's')
                map[i][j] = SECOND_PLAYER;
        }
    }

    map_file.close();
}

/**
 * You should call this method to get available moves for next step
*/
void get_possible_moves(int ans[MAP_SIZE][MAP_SIZE], int map[MAP_SIZE][MAP_SIZE], int row, int column)
{
    int size = MAP_SIZE * MAP_SIZE;
    bool graph[MAP_SIZE * MAP_SIZE][MAP_SIZE * MAP_SIZE];

    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            graph[i][j] = false;
        }
    }

    get_paths(row, column, graph);
    DFS(graph, ans, map, (row * MAP_SIZE) + column);
}

/**
 * Apllys the move to map that passed to method
*/
void move(int row, int column, int map[MAP_SIZE][MAP_SIZE], int result[MAP_SIZE][MAP_SIZE], int pos, int turn)
{
    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            result[i][j] = map[i][j];
        }
    }

    result[pos / MAP_SIZE][pos % MAP_SIZE] = BLOCKED;
    result[row][column] = turn;
}

/**
 * Saves the move to file
 * 
 * judge will read this move
*/
void save(int row, int column)
{
    ofstream move_file(MOVE_DIR);

    int move = column * MAP_SIZE + row;
    move_file << move << endl;

    move_file.close();
}

/**
 * Prints map to consol
 * 
 * you can use this method for debuging
 * 
 * + : available cell
 * - : blocked cell
*/
void print_map(int board[MAP_SIZE][MAP_SIZE])
{
    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            if (board[i][j] == ALLOWED)
                cout << "+";
            else
                cout << "-";
        }

        cout << endl;
    }
}

int read_position()
{
    fstream pos_file(BLUE_DIR);

    int pos;
    pos_file >> pos;
    pos_file.close();

    return pos;
}

void basic_move(int position)
{
    int temp[MAP_SIZE][MAP_SIZE];

    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            temp[i][j] = BLOCKED;
        }
    }

    get_possible_moves(temp, map, position / MAP_SIZE, position % MAP_SIZE);
    print_map(temp);

    for (int i = 0; i < MAP_SIZE; i++)
    {
        for (int j = 0; j < MAP_SIZE; j++)
        {
            if (temp[i][j] == ALLOWED)
            {
                save(i, j);
                return;
            }
        }
    }
}

int main()
{
    read_map();
    int pos = read_position();
    // cout << pos << endl;
    basic_move(pos);
}