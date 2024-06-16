import { Project } from "./Project"


export type Manager = {
    managerId?: string,
    managerName?: string,
    managerEmail?: string,
    managerPassword?: string,
    projectList?: Project[]
}