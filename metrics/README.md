# Metrics Gathering

We've created a bash script that runs daily to collect our metrics and store them as csv.

We use the [github_traffic_stats](https://github.com/seladb/github-traffic-stats) plugin to gather our metrics in three csv files.

Then we have created merge.sh to merge these metrics with the previous days csv and then sort by date order.
