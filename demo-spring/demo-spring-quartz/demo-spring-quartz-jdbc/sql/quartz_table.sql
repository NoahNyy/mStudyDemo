#
# quartz seems to work best with the driver mm.mysql-2.0.7-bin.jar
#
# please consider using mysql with innodb tables to avoid locking issues
#
# in your quartz properties file, you'll need to set
# org.quartz.jobstore.driverdelegateclass = org.quartz.impl.jdbcjobstore.stdjdbcdelegate
#

DROP TABLE IF EXISTS qrtz_fired_triggers;
DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
DROP TABLE IF EXISTS qrtz_scheduler_state;
DROP TABLE IF EXISTS qrtz_locks;
DROP TABLE IF EXISTS qrtz_simple_triggers;
DROP TABLE IF EXISTS qrtz_simprop_triggers;
DROP TABLE IF EXISTS qrtz_cron_triggers;
DROP TABLE IF EXISTS qrtz_blob_triggers;
DROP TABLE IF EXISTS qrtz_triggers;
DROP TABLE IF EXISTS qrtz_job_details;
DROP TABLE IF EXISTS qrtz_calendars;


CREATE TABLE qrtz_job_details (
    sched_name varchar(120) NOT NULL,
    job_name varchar(200) NOT NULL,
    job_group varchar(200) NOT NULL,
    description varchar(250) NULL,
    job_class_name varchar(250) NOT NULL,
    is_durable varchar(1) NOT NULL,
    is_nonconcurrent varchar(1) NOT NULL,
    is_update_data varchar(1) NOT NULL,
    requests_recovery varchar(1) NOT NULL,
    job_data blob NULL,
    PRIMARY KEY (sched_name, job_name, job_group)
);

CREATE TABLE qrtz_triggers (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    job_name varchar(200) NOT NULL,
    job_group varchar(200) NOT NULL,
    description varchar(250) NULL,
    next_fire_time bigint(13) NULL,
    prev_fire_time bigint(13) NULL,
    priority integer NULL,
    trigger_state varchar(16) NOT NULL,
    trigger_type varchar(8) NOT NULL,
    start_time bigint(13) NOT NULL,
    end_time bigint(13) NULL,
    calendar_name varchar(200) NULL,
    misfire_instr smallint(2) NULL,
    job_data blob NULL,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, job_name, job_group)
        REFERENCES qrtz_job_details (sched_name, job_name, job_group)
);

CREATE TABLE qrtz_simple_triggers (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    repeat_count bigint(7) NOT NULL,
    repeat_interval bigint(12) NOT NULL,
    times_triggered bigint(10) NOT NULL,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group)
        REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_cron_triggers (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    cron_expression varchar(200) NOT NULL,
    time_zone_id varchar(80),
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group)
        REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_simprop_triggers (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    str_prop_1 varchar(512) NULL,
    str_prop_2 varchar(512) NULL,
    str_prop_3 varchar(512) NULL,
    int_prop_1 int NULL,
    int_prop_2 int NULL,
    long_prop_1 bigint NULL,
    long_prop_2 bigint NULL,
    dec_prop_1 numeric(13, 4) NULL,
    dec_prop_2 numeric(13, 4) NULL,
    bool_prop_1 varchar(1) NULL,
    bool_prop_2 varchar(1) NULL,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group)
        REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_blob_triggers (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    blob_data blob NULL,
    PRIMARY KEY (sched_name, trigger_name, trigger_group),
    FOREIGN KEY (sched_name, trigger_name, trigger_group)
        REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_calendars (
    sched_name varchar(120) NOT NULL,
    calendar_name varchar(200) NOT NULL,
    calendar blob NOT NULL,
    PRIMARY KEY (sched_name, calendar_name)
);

CREATE TABLE qrtz_paused_trigger_grps (
    sched_name varchar(120) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    PRIMARY KEY (sched_name, trigger_group)
);

CREATE TABLE qrtz_fired_triggers (
    sched_name varchar(120) NOT NULL,
    entry_id varchar(95) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    instance_name varchar(200) NOT NULL,
    fired_time bigint(13) NOT NULL,
    sched_time bigint(13) NOT NULL,
    priority integer NOT NULL,
    state varchar(16) NOT NULL,
    job_name varchar(200) NULL,
    job_group varchar(200) NULL,
    is_nonconcurrent varchar(1) NULL,
    requests_recovery varchar(1) NULL,
    PRIMARY KEY (sched_name, entry_id)
);

CREATE TABLE qrtz_scheduler_state (
    sched_name varchar(120) NOT NULL,
    instance_name varchar(200) NOT NULL,
    last_checkin_time bigint(13) NOT NULL,
    checkin_interval bigint(13) NOT NULL,
    PRIMARY KEY (sched_name, instance_name)
);

CREATE TABLE qrtz_locks (
    sched_name varchar(120) NOT NULL,
    lock_name varchar(40) NOT NULL,
    PRIMARY KEY (sched_name, lock_name)
);


COMMIT;
