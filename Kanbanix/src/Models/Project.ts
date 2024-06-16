import { Task } from "./Task"




export type Project = {
    projectId?: string,
    projectName?: string,
    projectDesc?: string,
    projectTasks?: Task[] 
}