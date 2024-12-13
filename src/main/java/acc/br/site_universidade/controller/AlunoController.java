package acc.br.site_universidade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import acc.br.site_universidade.model.Aluno;
import acc.br.site_universidade.model.Endereco;
import acc.br.site_universidade.repository.AlunoRepository;
import acc.br.site_universidade.service.ViaCepService;

@Controller
@RequestMapping("/")
@SessionAttributes("aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @GetMapping
    public String mostrarPaginaInicial(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "index";
    }

    @PostMapping("/registro")
    public String registrarAluno(@ModelAttribute("aluno") Aluno aluno, Model model) {
        alunoRepository.save(aluno);
        model.addAttribute("aluno", aluno);
        return "revisaoRegistro";
    }

    @PostMapping("/confirmar")
    public String confirmarRegistro(@ModelAttribute("aluno") Aluno aluno, Model model) {
        alunoRepository.save(aluno);
        model.addAttribute("aluno", aluno);
        return "redirect:/bemvindo/" + aluno.getId();
    }

    @PostMapping("/login")
    public String login(@RequestParam("nome") String nome, @RequestParam("senha") String senha, RedirectAttributes redirectAttributes) {
        Aluno aluno = alunoRepository.findByNome(nome);
        if (aluno != null && senha.equals(aluno.getSenha())) {
            return "redirect:/bemvindo/" + aluno.getId();
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Nome ou senha incorreto! Ou aluno não registrado!");
            return "redirect:/";
        }
    }


    @GetMapping("/buscarCidade")
    @ResponseBody
    public String buscarCidade(@RequestParam("cep") String cep) {
        Endereco endereco = viaCepService.getEnderecoPorCep(cep);
        return endereco != null ? endereco.getLocalidade() : "";
    }

    @PostMapping("/cancelar")
    public String cancelarRegistro(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/bemvindo/{id}")
    public String mostrarPaginaBemVindo(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("aluno", aluno);
        return "bemVindo";
    }

    @GetMapping("/editar/{id}")
    public String mostrarPaginaEdicao(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("aluno", aluno);
        return "editarAluno";
    }

    @PostMapping("/editar")
    public String editarRegistro(@ModelAttribute("aluno") Aluno aluno, RedirectAttributes redirectAttributes) {
        Aluno alunoExistente = alunoRepository.findById(aluno.getId()).orElse(null);
        if (alunoExistente != null) {
            alunoExistente.setNome(aluno.getNome());
            alunoExistente.setCep(aluno.getCep());
            alunoExistente.setCidade(aluno.getCidade());
            alunoExistente.setCurso(aluno.getCurso());
            alunoExistente.setDataNascimento(aluno.getDataNascimento());
            alunoRepository.save(alunoExistente);
            redirectAttributes.addFlashAttribute("mensagem", "Informações atualizadas com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Aluno não encontrado.");
        }
        return "redirect:/bemvindo/" + aluno.getId();
    }
    
    @GetMapping("/deletar/{id}")
    public String deletarRegistro(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        alunoRepository.delete(aluno);
        redirectAttributes.addFlashAttribute("mensagem", "Registro cancelado com sucesso.");
        return "redirect:/";
    }

}
