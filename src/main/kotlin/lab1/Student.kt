package lab1

import java.time.Year

class Student(name: String, age: Int) : Human(name, age) {
    val courses = ArrayList<CourseRecord>()

    fun addCourse(course: CourseRecord) {
        courses.add(course)
    }

    fun weightedAverage(): Double {
        var sum = 0.0
        var totalCredits = 0
        for (course in courses) {
            sum += course.grade * course.credits
            totalCredits += course.credits
        }
        return sum / totalCredits
    }

    fun weightedAverage(year: Year): Double {
        var sum = 0.0
        var totalCredits = 0
        for (course in courses) {
            if (course.yearCompleted == year.value) {
                sum += course.grade * course.credits
                totalCredits += course.credits
            }
        }
        return sum / totalCredits
    }

    fun minMaxGrades(): Pair<Double, Double> {
        var minimum = courses[0].grade
        var maximum = courses[0].grade
        for (course in courses) {
            if (course.grade < minimum) {
                minimum = course.grade
            }
            if (course.grade > maximum) {
                maximum = course.grade
            }
        }
        return Pair(minimum, maximum)
    }
}