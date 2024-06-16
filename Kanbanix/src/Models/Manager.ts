import { Project } from "./project";

export type Manager = {
    managerId?: string,
    managerName?: string,
    managerEmail?: string,
    managerPassword?: string,
    projectList?: Project[]
}