module "security_group_database" {
  source              = "./modules/securitygroup"
  security_group_name = "database-sg"
  inbound_port        = [3306]
  vpc_id              = data.aws_vpc.infrastructure_vpc.id
}

module "security_group_alb" {
  source              = "./modules/securitygroup"
  security_group_name = "alb-sg"
  inbound_port        = [80]
  vpc_id              = data.aws_vpc.infrastructure_vpc.id
}

module "security_group_ecs" {
  source              = "./modules/securitygroup"
  security_group_name = "ecs-sg"
  inbound_port        = [80, 8090]
  vpc_id              = data.aws_vpc.infrastructure_vpc.id
}

module "database" {
  source             = "./modules/database"
  db_subnet_name     = "db-group-subnet"
  subnet_ids         = [data.aws_subnet.database_subnet.id, data.aws_subnet.database_readreplica_subnet.id]
  db_identifier      = "master-rds-instance"
  db_name            = "db_assiduite"
  db_user            = "root"
  db_password        = "ECS-AWSFargate"
  db_engine          = "mysql"
  db_version         = "5.7.37"
  db_instance_class  = "db.t3.micro"
  availability_zones = [data.aws_subnet.database_subnet.availability_zone, data.aws_subnet.database_readreplica_subnet.availability_zone]
  database_sg_id     = module.security_group_database.security_group_id
}

module "ecs_alb" {
  source                = "./modules/alb"
  alb_name              = "ecs-alb"
  alb_sg_id             = module.security_group_alb.security_group_id
  alb_subnet_ids        = [data.aws_subnet.ecs_1_subnet.id, data.aws_subnet.ecs_2_subnet.id, ]
  targetgroup_name      = "ecs-TG"
  vpc_id                = data.aws_vpc.infrastructure_vpc.id
  alb_internal          = false
  healthy_threshold     = 2
  unhealthy_threshold   = 5
  health_check_interval = 30
  health_check_path     = "/login"
  health_check_timeout  = 10
  target_type           = "ip"
}

module "ecs" {
  source                    = "./modules/ecs"
  cluster_name              = "assiduite-cluster"
  service_name              = "assiduite-service"
  task_name                 = "assiduite-task-definition"
  ecr_repository_url        = "625243961866.dkr.ecr.eu-north-1.amazonaws.com/assiduite"
  app_port                  = 8090
  database_endpoint         = module.database.database_endpoint
  database_username         = module.database.database_username
  database_name             = module.database.database_name
  database_password         = module.database.database_password
  desired_count             = 2
  ecs_subnets_ids           = [data.aws_subnet.ecs_1_subnet.id, data.aws_subnet.ecs_2_subnet.id]
  service_security_group_id = module.security_group_ecs.security_group_id
  alb_target_group_arn      = module.ecs_alb.alb_target_group_arn
}

