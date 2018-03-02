#!/usr/bin/env bash
../build/cassandra-loader -host 10.94.153.21 -f cos_goid_out.csv -schema "cos.goid(goid, id, isopen, objects, resgrp)" -boolStyle TRUE_FALSE
