# Cache Video Distribution Optimization

This repository contains the source code for a system that optimizes the distribution of cache video files in a server network, as presented in the Google Hashcode 2017 problem. The system is designed to minimize the average waiting time for all requests by strategically placing cached video files on servers and data centers based on anticipated user requests and network latencies.

## Problem Description

The problem at hand is to optimize the distribution of cache video files in a server network in order to minimize the average waiting time for all requests, as presented in Google Hashcode 2017. We are given the following information:

- The video files that will be requested, and the quantities associated with each endpoint.
- The latencies between all endpoints, servers, and data centers.

## Google Hashcode Competition

Google Hashcode is an annual programming competition organized by Google for students and professionals across Europe, the Middle East, and Africa. Participants are given four hours to process a large dataset, find solutions to a problem, and upload their solutions to the judge system. The goal of the competition is to encourage the development of innovative and efficient algorithms for solving real-world problems.

## Results

This implementation has a built-in judge system which works as described in the task docs and shows the scores for each individual file at the end.

| File Name                  | Score  |
|----------------------------|--------|
| me_at_the_zoo.txt          | 512283 |
| trending_today.txt         | 499966 |
| videos_worth_spreading.txt | 620838 |
| kittens.txt                | 907731 |

Score = 2540819

Solution files can be found in the `solution
