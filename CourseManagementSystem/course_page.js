const apiUrl = "http://localhost:8080/courses"; // 后端 API 基础路径

// 模拟用户角色
const role = "teacher"; // 可设置为 "student", "teacher", 或 "admin"

// 页面加载时初始化对应界面
document.addEventListener("DOMContentLoaded", () => {
    if (role === "student") {
        initStudentInterface();
    } else if (role === "teacher") {
        initTeacherInterface();
    } else if (role === "admin") {
        initAdminInterface();
    }
});

// 学生界面初始化
async function initStudentInterface() {
    document.getElementById("studentInterface").classList.remove("d-none");

    const courseId = 1; // 示例课程 ID，可动态修改
    const courseDetails = await fetchCourseDetails(courseId);

    const courseDetailsDiv = document.getElementById("courseDetails");
    courseDetailsDiv.innerHTML = `
        <h3>${courseDetails.name}</h3>
        <p><strong>Planned Hours:</strong> ${courseDetails.plannedHours}</p>
        <p><strong>Description:</strong> ${courseDetails.description}</p>
        <a href="${courseDetails.syllabusUrl}" class="btn btn-primary">Download Syllabus</a>
    `;
}

// 教师界面初始化
async function initTeacherInterface() {
    document.getElementById("teacherInterface").classList.remove("d-none");

    // 绑定添加课程表单
    document.getElementById("addCourseForm").addEventListener("submit", async (e) => {
        e.preventDefault();

        const course = {
            name: document.getElementById("courseName").value,
            description: document.getElementById("courseDescription").value,
            plannedHours: document.getElementById("courseHours").value,
            syllabusUrl: "http://example.com/syllabus.pdf", // 示例 URL，可通过实际上传逻辑生成
        };

        const response = await fetch(`${apiUrl}/courses`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(course),
        });

        if (response.ok) {
            alert("Course added successfully!");
            loadTeacherCourses();
        } else {
            alert("Failed to add course.");
        }
    });

    // 加载教师课程列表
    loadTeacherCourses();
}

async function loadTeacherCourses() {
    const response = await fetch(`${apiUrl}/courses`);
    const courses = await response.json();

    const courseList = document.getElementById("teacherCourseList");
    courseList.innerHTML = "";

    courses.forEach((course) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.description}</td>
            <td>${course.plannedHours}</td>
            <td>
                <button class="btn btn-sm btn-warning edit-course" data-id="${course.id}">Edit</button>
                <button class="btn btn-sm btn-danger delete-course" data-id="${course.id}">Delete</button>
            </td>
        `;
        courseList.appendChild(row);
    });

    // 为编辑和删除按钮绑定事件
    document.querySelectorAll(".edit-course").forEach((button) => {
        button.addEventListener("click", () => editCourse(button.dataset.id));
    });

    document.querySelectorAll(".delete-course").forEach((button) => {
        button.addEventListener("click", () => deleteCourse(button.dataset.id));
    });
}
//编辑功能实现
async function editCourse(courseId) {
    // 获取当前课程详情
    const response = await fetch(`${apiUrl}/courses/${courseId}`);
    const course = await response.json();

    // 提示用户修改课程信息
    const newName = prompt("Edit Course Name:", course.name);
    const newDescription = prompt("Edit Course Description:", course.description);
    const newHours = prompt("Edit Planned Hours:", course.plannedHours);

    if (newName && newDescription && newHours) {
        // 更新课程信息
        const updatedCourse = {
            name: newName,
            description: newDescription,
            plannedHours: parseInt(newHours, 10),
        };

        const updateResponse = await fetch(`${apiUrl}/courses/${courseId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(updatedCourse),
        });

        if (updateResponse.ok) {
            alert("Course updated successfully!");
            loadTeacherCourses(); // 重新加载课程列表
        } else {
            alert("Failed to update course.");
        }
    }
}
//删除功能的实现
async function deleteCourse(courseId) {
    if (confirm("Are you sure you want to delete this course?")) {
        const response = await fetch(`${apiUrl}/courses/${courseId}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert("Course deleted successfully!");
            loadTeacherCourses(); // 重新加载课程列表
        } else {
            alert("Failed to delete course.");
        }
    }
}


// 管理员界面初始化
async function initAdminInterface() {
    document.getElementById("adminInterface").classList.remove("d-none");

    const users = await fetchUsers();

    const userList = document.getElementById("userList");
    userList.innerHTML = "";

    users.forEach((user) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.role}</td>
            <td>${user.createdAt}</td>
        `;
        userList.appendChild(row);
    });
}

async function fetchUsers() {
    const response = await fetch(`${apiUrl}/users`);
    return response.json();
}

// 获取课程详情
async function fetchCourseDetails(courseId) {
    const response = await fetch(`${apiUrl}/courses/${courseId}`);
    return response.json();
}
