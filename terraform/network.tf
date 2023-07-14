data "aws_vpc" "infrastructure_vpc" {
  id = "vpc-02aec78b7938f4665"
}

data "aws_subnet" "ecs_1_subnet" {
  id = "subnet-02b28078f59c93515"
}
data "aws_subnet" "ecs_2_subnet" {
  id = "subnet-0ae9a4eed0850e590"
}

data "aws_subnet" "first_subnet" {
  id = "subnet-02b28078f59c93515"
}
data "aws_subnet" "second_subnet" {
  id = "subnet-0ae9a4eed0850e590"
}

data "aws_subnet" "database_subnet" {
  id = "subnet-0ef836e9e96c80b81"
}

data "aws_subnet" "database_readreplica_subnet" {
  id = "subnet-03162df9ec7807feb"
}