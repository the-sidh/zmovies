#!/usr/bin/env bash

echo 'Creating application user and db'

mongo zmovies \
        --host localhost:27017 \
        --authenticationDatabase admin \
        --eval "db.createUser({user: 'zmovies', pwd: 'zmovies', roles:[{role:'dbOwner', db: 'zmovies'}]});"
