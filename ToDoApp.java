package ToDoApp_package;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ToDoApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JButton addButton, deleteButton;

    public ToDoApp() {
        setTitle("Stylish Card To-Do List");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ==== Components ====
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskList.setCellRenderer(new TaskCardRenderer());

        taskField = new JTextField();
        taskField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        addButton = createStyledButton("➕ Add Task", new Color(72, 201, 176));
        deleteButton = createStyledButton("🗑 Delete Task", new Color(231, 76, 60));

        // ==== Layout ====
        JPanel inputPanel = new JPanel(new BorderLayout(8, 8));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(deleteButton);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        getContentPane().setBackground(new Color(250, 250, 250));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ==== Event Listeners ====
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        taskField.addActionListener(e -> addTask());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Task cannot be empty!");
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    public static void main(String[] args) {
        // Use Nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new ToDoApp().setVisible(true);
        });
    }
}

// ==== Custom Card Renderer ====
class TaskCardRenderer extends JLabel implements ListCellRenderer<String> {
    public TaskCardRenderer() {
        setOpaque(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list, String value, int index,
            boolean isSelected, boolean cellHasFocus) {

        setText(value);

        // Alternate colors for cards
        Color evenColor = new Color(236, 240, 241);
        Color oddColor = new Color(223, 228, 234);
        setBackground((index % 2 == 0) ? evenColor : oddColor);

        // Highlight if selected
        if (isSelected) {
            setBackground(new Color(52, 152, 219));
            setForeground(Color.WHITE);
        } else {
            setForeground(Color.BLACK);
        }

        return this;
    }
}
