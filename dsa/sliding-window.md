
# Sliding Window Final Cheat Sheet (Clean Version)

## The 3 Patterns (Correct Order)

1.  **Pattern 1 -- Variable-size, Shrink-Until-Valid**
2.  **Pattern 2 -- Variable-size, Jump-Left by Index**
3.  **Pattern 3 -- Fixed-Size Window**

------------------------------------------------------------------------

# Decision Flowchart

    START
     |
     |-- A) Does the problem say "substring/subarray of length K" or "size K"?
     |        YES -> Pattern 3 (Fixed-size)
     |        NO
     |
     |-- B) When invalid, can you compute the new left in O(1) from an index map
     |      (e.g., lastSeen[char] -> jump left directly)?
     |        YES -> Pattern 2 (Jump-left)
     |        NO
     |
     |-- C) Is it "longest/minimum/count" subject to an "at most / at least" constraint
     |      based on counts/sum/frequency?
     |        YES -> Pattern 1 (Shrink-until-valid)
     |        NO -> Usually Pattern 1 (safe default)

------------------------------------------------------------------------

# Pattern 1 -- Shrink Until Valid (Workhorse)

**Trigger words:**\
"at most K...", "minimum window...", "longest such that...",\
"subarray sum ≥ ... (min length)", "replace/flip at most K"

**Pointer rule:**\
Expand `right`. While invalid → move `left` repeatedly.

**Mental Model:**\
Validity controls window size. You don't know how far to move left, so
shrink in a loop.

## Problems

- Minimum Window Substring\
        https://leetcode.com/problems/minimum-window-substring/

- Longest Repeating Character Replacement\
         https://leetcode.com/problems/longest-repeating-character-replacement/

- Longest Substring with At Most K Distinct Characters\
         https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

- Minimum Size Subarray Sum\
         https://leetcode.com/problems/minimum-size-subarray-sum/

- Max Consecutive Ones III\
          https://leetcode.com/problems/max-consecutive-ones-iii/

- Subarrays with K Different Integers\
         https://leetcode.com/problems/subarrays-with-k-different-integers/

**Exactly K Trick:**\
exactlyK = atMost(K) - atMost(K-1)

------------------------------------------------------------------------

# Pattern 2 -- Jump Left Using Index

**Trigger words:**\
"no repeating", "unique characters", "last occurrence", "duplicate
inside window"

**Pointer rule:**\
Expand `right`. If violation →\
`left = max(left, lastSeen[element] + 1)`

**Mental Model:**\
Violation caused by one known index. Jump directly to safe spot.

## Problems

-   3.  Longest Substring Without Repeating Characters\
        https://leetcode.com/problems/longest-substring-without-repeating-characters/

-   1695. Maximum Erasure Value\
          https://leetcode.com/problems/maximum-erasure-value/

-   904. Fruit Into Baskets\
         https://leetcode.com/problems/fruit-into-baskets/

------------------------------------------------------------------------

# Pattern 3 -- Fixed-Size Window

**Trigger words:**\
"length K", "size K", "exactly K", "every window of length K"

**Pointer rule:**\
Window size is fixed. Slide as a block.

**Mental Model:**\
Window size drives movement. Not validity.

## Problems

-   643. Maximum Average Subarray I\
         https://leetcode.com/problems/maximum-average-subarray-i/

-   1456. Maximum Number of Vowels in a Substring of Given Length\
          https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/

-   438. Find All Anagrams in a String\
         https://leetcode.com/problems/find-all-anagrams-in-a-string/

-   567. Permutation in String\
         https://leetcode.com/problems/permutation-in-string/

-   239. Sliding Window Maximum\
         https://leetcode.com/problems/sliding-window-maximum/

------------------------------------------------------------------------

# Ultra-Fast Recognition Checklist

-   Fixed length K? → Pattern 3\
-   At most / at least constraint? → Pattern 1\
-   No repeats + last seen index? → Pattern 2\
-   Counting exactly K? → Pattern 1 with atMost trick
