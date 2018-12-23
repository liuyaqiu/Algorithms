#include <iostream>
#include <vector>

using namespace std;

void merge(vector<int>& nums) {
    int n = nums.size();
    int m = n / 2;
    vector<int> tmp(m, 0);
    int a = 0, b = m;
    int k = 0;
    while(k < m) {
        if(nums[a] <= nums[b]) {
            tmp[k++] = nums[a++];
        }
        else {
            tmp[k++] = nums[b++];
        }
    }
    int t = m;
    while(a < m) {
        nums[t++] = nums[a++];
    }
    for(int i = 0; i < m; i++) {
        nums[i] = tmp[i];
    }
    a = m;
    k = 0;
    int end = b;
    while(a < end && b < n) {
        if(nums[a] <= nums[b]) {
            tmp[k++] = nums[a++];
        }
        else {
            tmp[k++] = nums[b++];
        }
    }
    while(a < end) {
        tmp[k++] = nums[a++];
    }
    while(b < n) {
        tmp[k++] = nums[b++];
    }
    for(int i = m; i < n; i++) {
        nums[i] = tmp[i - m];
    }
}

int main() {
    int n;
    cin >> n;
    vector<int> nums(2 * n, 0);
    for(int i = 0; i < 2 * n; i++)
        cin >> nums[i];
    for(auto item: nums)
        cout << item << " ";
    cout << endl;
    merge(nums);
    for(auto item: nums)
        cout << item << " ";
    cout << endl;
    return 0;
}
