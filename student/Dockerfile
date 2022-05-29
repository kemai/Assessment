FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-debian-slim

COPY out/production/student/ /tmp
WORKDIR /tmp
CMD java uk.ac.leedsbeckett.StudentApplication



ADD studentdb.h2.db  /tmp/studentdb.h2.db