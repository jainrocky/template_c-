/***
                                   _________________________
                                  |                         |           ________________________________________
                                  |_________________________|          |   __   __  __   __   __  ___  __       |
                                  |           * *           |          |  |__  |   |  | | _| |__|  |  |  | |\ | |
                                  |          *@IN*          |          |   __| |__ |__| | \  |    _|_ |__| | \| |
                                  |___________*_*___________|          |________________________________________|
                                  |                         |
                                  |_________________________|

**/

#include<bits/stdc++.h>
#include<chrono>
#define BOOSTER ios_base::sync_with_stdio(0); cin.tie(0); cout.tie(0);
#define REPEAT intll t; cin >> t; for(intll iterator=0; iterator<t; iterator++)
#define BLN 1000000000
#define LKH 100000
#define MLN 1000000
typedef long long intll;
typedef long intl;
using namespace std;
using namespace std::chrono;

template<typename E>
inline void write(const E &tar, string end="\n"){ cout << tar << end; }
template<typename E, typename L>
inline void write(const E *tar, const L len, string end=" "){ for(L i=0; i<len; i++) cout << tar[i] << end; }
template<typename E>
inline void write(const vector<E> &tar, string end=" "){ for(intll i=0; i<tar.size(); i++) cout << tar[i] << end; }
template<typename E>
inline void read(E &tar){ cin >> tar; }
template<typename E, typename L>
inline void read(E *tar, const L len){ for(L i=0; i<len; i++) cin >> tar[i]; }
template<typename E, typename L>
inline void read(vector<E> &tar, const L len){if(tar.size()>0) for(L i=0; i<tar.size(); i++) cin >> tar[i]; else for(L i=0; i<len; i++){ E temp; cin >> temp; tar.push_back(temp);}}

void clockCounterPrinter(vector<double> readings, double times){
    double duration=accumulate(readings.begin(), readings.end(), 0.0);
    duration /= times;
    string unit=" micro(s)";
    write(string(42, '-'));
    cout<<"| Max-TimeTaken |"<<setw(15)<<*max_element(readings.begin(), readings.end())<<unit<<"|\n";
    write(string(42, '-'));
    cout<<"| Min-TimeTaken |"<<setw(15)<<*min_element(readings.begin(), readings.end())<<unit<<"|\n";
    write(string(42, '-'));
    cout<<"| Avg-TimeTaken |"<<setw(15)<<duration<<unit<<"|\n";
    write(string(42, '-'));
    write("\tTotal steps: "+to_string(int(times)));

}

vector<double> clockCount(void (*function)(), double times=10){
    vector<double> readings(times);
    for(int i=0; i<times; i++){
        high_resolution_clock::time_point t1 = high_resolution_clock::now();
        function();
        high_resolution_clock::time_point t2 = high_resolution_clock::now();
        readings[i]=duration_cast<microseconds>( t2 - t1 ).count();
    }
    clockCounterPrinter(readings, times);
    return readings;
}


template<typename E>
E power(E val, intll expo){
    E res = (E)1;
    while(expo > 1LL){
        if(expo % 2LL==0LL) val *= val,  expo/=2LL;
        else res *= val, --expo;
    }
    val *= res;
    return val;
}

template<typename E>
void cookPrimes(vector<E> &primes, intll upto=MLN){
    bool p[upto];
    memset(p, false, sizeof(p));
    for(intll  i = 2 ;i*i <= upto ; i++){
        intll m = i * i;
        if(p[i] == false){
            while( m <= upto){
                p[m] = true;
                m += i;
            }
        }
    }
    for(intll i = 2 ; i <= upto; i++){
        if(p[i] == false)
            primes.push_back(i);
    }
}

/** How to use clockCount?
    clockCount([](){ /// must use lambda function with no return type
                someFunction(arguments); ///  to measure time.
               });
*/


int main(){
    BOOSTER
    clockCount([](){
            vector<int> p;
            cookPrimes(p);
        },100);
    return 0;
}
