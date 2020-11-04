#!/usr/bin/python

import sys
import os


def create_project_folders(projectFolders):
    create_folder(fullServicesFolder)
    for projectIndex in range(len(projectFolders)):
        projectFolder = fullServicesFolder + "/" + projectFolders[projectIndex]
        create_folder(projectFolder)
        open(projectFolder + "/build.gradle", "w").close()
        create_project_subfolders(projectFolder)
        f = open(os.getcwd() + "/settings.gradle", "a")
        f.write("include '" + servicesFolder +
                ":" + projectFolders[projectIndex] + "'\n")
        f.close()


def create_project_subfolders(projectFolder):
    # print(projectFolder)
    for srcFolderIndex in range(len(srcFolders)):
        srcFolder = projectFolder + "/" + srcFolders[srcFolderIndex] + "/"
        # print(srcFolder)
        create_folder(srcFolder)


def create_folder(path):
    try:
        os.mkdir(path)
    except OSError:
        print ("create_folder failed  : %s" % path)
    else:
        print ("create_folder succeed : %s" % path)


servicesFolder = sys.argv[1]
projectFolders = sys.argv[2].split(',')
# projectFolders = "project1 project2".split(' ')
# servicesFolderName = "/services"
srcFolders = "src src/main src/main/java src/main/resources src/test src/test/java src/test/resources".split(
    ' ')

currentPath = os.getcwd()
fullServicesFolder = currentPath + "/" + servicesFolder
create_project_folders(projectFolders)
