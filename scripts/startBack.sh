sudo -u postgres psql << EOF
CREATE DATABASE musicstreamerdb;
EOF

mvn spring-boot:run;
