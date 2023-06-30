#!/bin/bash

docker compose up -d
sleep 10
# first run this
chmod 777 $(pwd)

# comment above cmd and uncomment below lines to run with CUSTOM RULES
docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-weekly zap-api-scan.py -t  http://${ip}:8090/v3/api-docs -f openapi -r zap_report-${gitCommit}.html
exit_code=$?


# HTML Report

echo "Exit Code : $exit_code"

 if [[ ${exit_code} -ne 0 ]];  then
    echo "OWASP ZAP Report has either Low/Medium/High Risk. Please check the HTML Report"
    docker compose down
    exit 0;
   else
     docker compose down
    echo "OWASP ZAP did not report any Risk"
 fi;