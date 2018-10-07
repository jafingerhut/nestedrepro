set -Exeuo pipefail

function sample {
    lein clean
    echo "aot compiling ${expr_type} with depth ${depth}"
    NESTED_EXPR_TYPE="${expr_type}" NESTING_DEPTH="${depth}" lein uberjar
    echo "longest compiled file:"
    find target/uberjar/classes/nestedrepro -exec basename {} \; \
        | gawk '{ print length, $0 }' \
        | sort -rn \
        | head -1
    echo
}

#for expr_type in do try; do
for expr_type in trynestcatch; do
    for depth in $(seq 1 8); do
        sample
    done
done
