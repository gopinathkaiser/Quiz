package com.Quiz.QuestionService.Service;

import com.Quiz.QuestionService.Dao.QuestionDao;
import com.Quiz.QuestionService.Dao.QuizDao;
import com.Quiz.QuestionService.Model.Question;
import com.Quiz.QuestionService.Model.QuestionWrapper;
import com.Quiz.QuestionService.Model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

     @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByDao(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }


        public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Question> quetionFromDb = quiz.get().getQuestions();
            List<QuestionWrapper> questionFormUser = new ArrayList<>();

            for(Question q : quetionFromDb){
                QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                questionFormUser.add(qw);
            }
            return new ResponseEntity<>(questionFormUser, HttpStatus.CREATED);

        }

}
