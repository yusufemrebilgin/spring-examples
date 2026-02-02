#!/usr/bin/env bash

COUNT="$1"

if ! [[ "$COUNT" =~ ^[0-9]+$ ]]; then
  echo "Usage: ./run-orders.sh <count>" >&2
  echo "Error: count must be positive integer" >&2
  exit 1
fi

URL="${URL:-http://localhost:8080/api/v1/orders}"

for i in $(seq 1 "$COUNT"); do
  product_id=$((RANDOM % 10 + 1))
  status=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$URL?productId=${product_id}&quantity=1")
  echo "[$i/$COUNT] HTTP $status"
done
