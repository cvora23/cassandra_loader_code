#!/usr/bin/env bash
../build/cassandra-unloader -host 10.94.153.21 -f cos_goid.csv -schema "cos.goid(goid, id, isopen, objects, resgrp)" -boolStyle TRUE_FALSE
