module.exports = {
    title: "PiFlow Big Data Pipeline System",
    sidebar: {
        dashboard: "Dashboard",
        flow: "Flow",
        group: "Group",
        processes: "Process",
        template: "Template",
        data_source: "DataSource",
        schedule: "Schedule",
        stopHub: "StopHub",
        example: "Example",
        admin: "Admin",
    },
    page: {
        prev_text: "Previous",
        next_text: "Next"
    },
    tip: {
        tilte: "Piflow system tips",
        fault_content: "An unknown error occurred in the background run！",
        update_success_content: "update succeeded！",
        update_fail_content: "update failed！",
        save_success_content: "template saved successfully！",
        save_fail_content: "template save failed！",
        add_success_content: "added succeeded！",
        add_fail_content: "add failed！",
        run_success_content: "run succeeded！",
        run_fail_content: "run failed！",
        stop_success_content: "stop succeeded！",
        stop_fail_content: "stop failed！",
        get_success_content: "Data acquisition successful！",
        get_fail_content: "Data acquisition failed！",
        dubug_success_content: "dubug succeeded！",
        dubug_fail_content: "dubug failed！",
        delete_success_content: "deleted successfully！",
        delete_fail_content: "deletion failed！",
        request_fail_content: "Data request failed！",

        upload_success_content: "Template upload successful！",
        upload_fail_content: "Template upload failed！",
        download_success_content: "download successful！",
        download_fail_content: "download failed！",

        data_fail_content: "Data acquisition failed！",
    },
    modal: {
        placeholder: "please enter the content...",
        create_title: "Create",
        udate_title: "Update",
        template_title: "Template name",
        ok_text: "Submit",
        upload_text: "upload",
        confirm: "Confirm",
        cancel_text: "Cancel",
        delete_content: "Are you sure to delete",
        flow_name: "FlowName",
        group_name: "GroupName",
        driverMemory: "DriverMemory",
        executorNumber: "ExecutorNumber",
        executorMemory: "ExecutorMemory",
        executorCores: "ExecutorCores",
        description: "Description",
        type: "Type",
        name: "Name",
        addProperty: "AddProperty",
        jobName: "Name",
        jobClass: "Class",
        cronExpression: "Cron",
        upload: "Upload Jar",
        startDate: "StartDate",
        endDate: "EndDate",
        flowIsGroup: "Flow / Group",
        cron: "Cron",
    },
    flow_columns: {
        name: "Name",
        description: "description",
        CreateTime: "CreateTime",
        dataSourceType: "DataSourceType",
        action: "Actions"
    },
    schedule_columns: {
        cron: "Cron",
        type:'Type',
        name: "Name",
        planStartTime:"StartTime",
        planEndTime:"EndTime",
        status: "Status",
        action: "Actions"
    },
    StopHub_columns: {
        name: "Name",
        version: "Version",
        jarUrl: "JarUrl",
        status: "Status",
        action: "Actions"
    },
    progress_columns: {
        progress: "Progress",
        endTime: "EndTime",
        startTime: "StartTime",
        processType: "ProcessType",
        description: "Description",
        id: "ProcessGroupId",
        name: "Name",
        state: "State",
        action: "Actions"
    },
    template_columns: {
        name: "name",
        crtDttm: "CreateTime",
        action: "Actions"
    },
    admin_columns: {
        name: "Name",
        class: "Class",
        cron: "Cron",
        status: "Status",
        createTime: "CreateTime",
        action: "Actions"
    },
    homeInfo: {
        introduction_title: "Introduction",
        monitor_title: "Monitor",
        statistics_title: "Statistics",
        CPU_Disk: 'CPU disk usage',
        Memory_Disk: 'Memory disk usage',
        HDFS_Disk: 'HDFS disk usage',
        totalCapacity: 'total capacity',
        Used: 'Used',
        introduction_Info: "PiFlow is a big data pipeline system developed based on the distributed computing engine Spark. It realizes the flow configuration, operation and intelligent monitoring of big data collection, processing, storage and analysis in a WYSIWYG way, providing 100+ standardization Components, including Hadoop, Spark, MLlib, Hive, Solr, ElasticSearch, Redis, etc., supporting field-oriented and flexible secondary component development with superior performance.",
        flowStatistics: "Pipeline statistics, include the number of pipeline flows, the number of processors in the running state, and the number of processors in each running state.",
        scheduleStatistics: "Scheduling statistics, include the number of scheduling pipelines/pipeline groups, and the number of schedules in each state.",
        groupStatistics: "Pipeline group statistics, include the number of pipeline groups, the number of processors of the pipeline groups in the running state, and the number of processors in each running state.",
        OtherStatistics: "Other statistics include the number of DataSources, the number of custom component plug-ins StopsHub, and the number of templates.",
        ComponentStatistics: "Component statistics, include the number of data processing components Stop and the number of data processing component groups StopGroup.",
    }
}